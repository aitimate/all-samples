package com.example.s01tomcatwebsocket.utils;

import com.alibaba.fastjson2.JSONObject;
import com.example.s01tomcatwebsocket.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketUtil.class);

    /**
     * Session 与用户的映射
     */
    private static final Map<Session, String> SESSION_USER_MAP = new ConcurrentHashMap<>();

    /**
     * 用户与 Session 的映射
     */
    private static final Map<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 添加 Session
     */
    public static void addSession(Session session, String user) {
        USER_SESSION_MAP.put(user, session);
        SESSION_USER_MAP.put(session, user);
    }

    /**
     * 移除 Session
     */
    public static void removeSession(Session session) {
        String user = SESSION_USER_MAP.remove(session);
        if (!ObjectUtils.isEmpty(user)) {
            USER_SESSION_MAP.remove(user);
        }
    }

    /**
     * 群发消息/广播发送消息
     */
    public static <T extends Message> void broadcast(String type, T message) {
        String messageText = buildJSONMessage(type, message);
        for (Session session : SESSION_USER_MAP.keySet()) {
            sendJSONMessage(session, messageText);
        }
    }

    /**
     * 发送消息给指定用户
     */
    public static <T extends Message> void send(Session session, String type, T message) {
        String messageText = buildJSONMessage(type, message);
        sendJSONMessage(session, messageText);
    }

    /**
     * 发送消息给指定用户
     */
    public static <T extends Message> void send(String user, String type, T message) {
        Session session = USER_SESSION_MAP.get(user);
        if (session == null) {
            LOGGER.error("[send] user({}) 不存在", user);
            return;
        }
        send(session, type, message);
    }

    private static <T extends Message> String buildJSONMessage(String type, T message) {
        JSONObject messageObject = new JSONObject();
        messageObject.put("type", type);
        messageObject.put("body", message);
        return messageObject.toString();
    }

    /**
     * 发送消息
     */
    private static void sendJSONMessage(Session session, String messageText) {
        if (session == null) {
            LOGGER.error("[sendJSONMessage] 连接不存在");
            return;
        }
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            LOGGER.error("[sendJSONMessage] 连接已断开");
            return;
        }
        try {
            basic.sendText(messageText);
        } catch (IOException e) {
            LOGGER.error("[sendJSONMessage]\"{}\"发送失败: {}，session({})", messageText, e, session);
        }
    }
}
