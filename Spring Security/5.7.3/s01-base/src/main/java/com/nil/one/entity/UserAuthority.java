package com.nil.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * 
 * @TableName t_user_authority
 */
@TableName(value ="t_user_authority")
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
    private String value;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthority() {
        return value;
    }
}