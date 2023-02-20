package com.example.s01tomcatwebsocket.endpoint;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.s01tomcatwebsocket.message.AuthRequest;
import com.example.s01tomcatwebsocket.message.Message;
import com.example.s01tomcatwebsocket.message.handler.MessageHandler;
import com.example.s01tomcatwebsocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ServerEndpoint("/")
public class WebsocketServerEndpoint implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Map<String, MessageHandler<?>> HANDLERS = new HashMap<>();

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 初始化: 扫描所有我们写的 MessageHandler Bean，并存储到 HANDLERS (implements InitializingBean)
     */
    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(messageHandler -> {
                    HANDLERS.put(messageHandler.getType(), messageHandler);
                    logger.info("[InitializingBean::afterPropertiesSet] 就绪：{}", messageHandler.getType());
                });
        logger.info("[InitializingBean::afterPropertiesSet] 消息处理器数量：{}", HANDLERS.size());
    }

    /**
     * 前端请求连接时需要认证
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        logger.info("[onOpen] \n建连: {} \n\tfrom: session({})", config.getUserProperties(), session);
        List<String> accessTokenValues = session.getRequestParameterMap().get("accessToken");
        String accessToken = !CollectionUtils.isEmpty(accessTokenValues) ? accessTokenValues.get(0) : null;
        AuthRequest authRequest = new AuthRequest().setAccessToken(accessToken);
        MessageHandler<?> messageHandler = HANDLERS.get(AuthRequest.TYPE);
        if (messageHandler == null) {
            logger.error("[onOpen] 认证处理器缺失！\n\tfrom: session({})", session);
            return;
        }
        messageHandler.execute(session, authRequest);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info("[onClose] \n关闭: {} \n\tfrom: session({})", closeReason, session);
        WebSocketUtil.removeSession(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("[onError] \n错误: {} \n\tfrom: session({})", throwable, session);
    }

    @OnMessage
    public void onMessage(Session session, String rawMessage) {
        logger.info("[onMessage] \n消息: {} \n\tfrom: session({})", rawMessage, session); // 生产环境下，请设置成 debug 级别
        try {
            // <1> 消息读取: 原始消息数据
            JSONObject jsonMessage = JSON.parseObject(rawMessage);
            String type = jsonMessage.getString("type");
            String body = jsonMessage.getString("body");
            // <2> 消息处理器: 从消息处理器解析真实的消息范型(消息原型)
            MessageHandler<?> messageHandler = HANDLERS.get(type);
            if (messageHandler == null) {
                logger.error("[onMessage] [ERROR] 不存在该消息消息类型的处理器: {} \n\tfrom: session({})", type, session);
                return;
            }
            Class<?> messageClass = MessageHandler.getMessageClass(messageHandler);
            // <3> 消息处理: 消息体数据转为消息原型实例并处理
            messageHandler.execute(session, (Message) JSON.parseObject(body, messageClass));
        } catch (Exception exception) {
            logger.error("[onMessage] [ERROR] {} \n\tfrom: session({})", exception.getMessage(), session);
        }
    }
}