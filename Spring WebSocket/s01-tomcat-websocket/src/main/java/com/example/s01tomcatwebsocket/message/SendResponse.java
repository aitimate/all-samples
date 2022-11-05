package com.example.s01tomcatwebsocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
* 6-响应成功发送
* */
@Data
@Accessors(chain = true)
public class SendResponse implements Message {

    public static final String TYPE = "SEND_RESPONSE";

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;
}