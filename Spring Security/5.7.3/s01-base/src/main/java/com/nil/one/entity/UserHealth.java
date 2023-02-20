package com.nil.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName t_ss573s01_user_health
 */
@TableName(value ="t_ss573s01_user_health")
@Data
public class UserHealth implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String prop;

    /**
     * 
     */
    private Double value;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}