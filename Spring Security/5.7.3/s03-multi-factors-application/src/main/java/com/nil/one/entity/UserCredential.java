package com.nil.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户认证凭证表
 * @TableName t_ss573s03_user_credential
 */
@TableName(value ="t_ss573s03_user_credential")
@Data
public class UserCredential implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer uid;

    /**
     * 账号类型: 自建账号、手机号、邮箱、Token、(第三方)微信...
     */
    private String accType;

    /**
     * 
     */
    private String identifier;

    /**
     * 
     */
    private String credential;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}