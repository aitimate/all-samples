package com.example.s01tomcatwebsocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 3-广播用户成功加入群聊
 * */
@Data
@Accessors(chain = true)
public class JoinNotice implements Message {

    public static final String TYPE = "USER_JOIN_NOTICE_REQUEST";

    /**
     * 昵称
     */
    private String nickname;
}