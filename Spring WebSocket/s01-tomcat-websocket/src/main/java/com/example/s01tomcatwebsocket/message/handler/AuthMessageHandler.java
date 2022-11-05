package com.example.s01tomcatwebsocket.message.handler;

import com.example.s01tomcatwebsocket.message.*;
import com.example.s01tomcatwebsocket.utils.WebSocketUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.websocket.Session;

/**
 * 1-AuthRequest消息处理器
 */
@Component
public class AuthMessageHandler implements MessageHandler<AuthRequest> {

    @Override
    public void execute(Session session, Message rawMessage) {
        AuthRequest message = (AuthRequest) rawMessage;
        // 0-回送提示: 消息类型 AuthResponse
        if (ObjectUtils.isEmpty(message.getAccessToken())) {
            WebSocketUtil.send(session, AuthResponse.TYPE, new AuthResponse().setCode(1).setMessage("认证 accessToken 未传入"));
            return;
        }
        // 1-生成 session (为了简单，直接用 accessToken 作为用户标识)
        WebSocketUtil.addSession(session, message.getAccessToken());

        // 2-假定已认证成功
        WebSocketUtil.send(session, AuthResponse.TYPE, new AuthResponse().setCode(0));

        // 3-通知所有人
        WebSocketUtil.broadcast(JoinNotice.TYPE, new JoinNotice().setNickname(message.getAccessToken()));
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}