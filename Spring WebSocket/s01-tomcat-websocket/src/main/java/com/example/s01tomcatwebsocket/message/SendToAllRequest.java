package com.example.s01tomcatwebsocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 5-发送群聊消息
 * */
@Data
@Accessors(chain = true)
public class SendToAllRequest implements Message {

    public static final String TYPE = "SEND_TO_ALL_REQUEST";

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;
    
}