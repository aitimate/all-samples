package com.example.s01tomcatwebsocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 4-发送定向消息
 * */
@Data
@Accessors(chain = true)
public class SendToOneRequest implements Message {

    public static final String TYPE = "SEND_TO_ONE_REQUEST";

    /**
     * 发送给的用户
     */
    private String toUser;
    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;
    
}