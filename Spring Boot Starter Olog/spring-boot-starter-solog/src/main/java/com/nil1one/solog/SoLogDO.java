package com.nil1one.solog;

import lombok.Data;

import java.util.Date;

@Data
public class SoLogDO {
    private Long uid;
    private String username;
    private String location;
    private String desc;
    private String message;
    private Integer duration;
    private Integer status;
    private Date time;
}
