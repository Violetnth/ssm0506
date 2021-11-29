package com.shelley.global;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局通用的返回类
 */
@Data
public class Msg {
    //状态码 100-成功 200-失败
    private int code;
    //提示信息
    private String msg;
    //用户要返回浏览器的数据集合
    private Map<String, Object> extend = new HashMap<>();

    public static Msg success() {
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("操作成功！");
        return result;
    }
    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("操作失败！");
        return result;
    }

    public Msg add(String key , Object value){
        this.getExtend().put(key,value);
        return this;
    }

}
