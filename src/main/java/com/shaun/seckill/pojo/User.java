package com.shaun.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Shaun
 * @since 2022-07-09
 */
@Data
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID shoujihaoma
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("nickname")
    private String nickname;

    /**
     * MD5二次加密
     */
    @TableField("pasword")
    private String pasword;

    @TableField("salt")
    private String salt;

    /**
     * 头像
     */
    @TableField("head")
    private String head;

    /**
     * 注册时间
     */
    @TableField("register_date")
    private Date registerDate;

    /**
     * 最后一次登录时间
     */
    @TableField("last_login_date")
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;


}
