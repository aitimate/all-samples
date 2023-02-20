package com.example.s01tomcatwebsocket.message.handler;

import com.example.s01tomcatwebsocket.message.*;
import com.example.s01tomcatwebsocket.utils.WebSocketUtil;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * 2-SendToOneRequest单发消息处理器
 * */
@Component
public class SendToOneHandler implements MessageHandler<SendToOneRequest> {

    @Override
    public void execute(Session session, Message rawMessage) {
        SendToOneRequest message = (SendToOneRequest) rawMessage;
        // 1-回送已经成功发送(假)
        SendResponse sendResponse = new SendResponse().setMsgId(message.getMsgId()).setCode(0);
        WebSocketUtil.send(session, SendResponse.TYPE, sendResponse);
        // 2-接收到消息 转换→ 要发送的消息
        SendToUserRequest sendToUserRequest = new SendToUserRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        // 3-发送
        WebSocketUtil.send(message.getToUser(), SendToUserRequest.TYPE, sendToUserRequest);
    }

    @Override
    public String getType() {
        return SendToOneRequest.TYPE;
    }

}