package com.shelley.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * (Employee)实体类
 *
 * @author makejava
 * @since 2021-05-06 17:08:38
 */
@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 674404256285196124L;
    
    private Integer id;

    @Pattern(regexp = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d·s]{2,20}$",message = "姓名格式不合法")
    private String name;
    
    private Integer did;
    
    private String gender;

    @Email(message = "邮箱格式不合法")
    private String email;

    private Dept dept;

}