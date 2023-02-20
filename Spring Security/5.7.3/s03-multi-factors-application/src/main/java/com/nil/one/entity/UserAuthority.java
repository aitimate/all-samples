package com.nil.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * 用户身份权限表
 * @TableName t_ss573s03_user_authority
 */
@TableName(value ="t_ss573s03_user_authority")
@Data
public class UserAuthority implements Serializable, GrantedAuthority {
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
     * 
     */
    private String authority;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}