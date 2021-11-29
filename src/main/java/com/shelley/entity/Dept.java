package com.shelley.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Dept)实体类
 *
 * @author makejava
 * @since 2021-05-06 17:08:59
 */
@Data
public class Dept implements Serializable {
    private static final long serialVersionUID = 222227481253025543L;
    
    private Integer id;
    
    private String name;


}