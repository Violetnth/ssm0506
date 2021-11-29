package com.shelley.controller;

import com.shelley.entity.Dept;
import com.shelley.global.Msg;
import com.shelley.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Dept)表控制层
 *
 * @author makejava
 * @since 2021-05-06 17:08:59
 */
@RestController
public class DeptController {
    /**
     * 服务对象
     */
    @Autowired
    private DeptService deptService;

    @GetMapping("depts")
    public Msg getDepts() {
        List<Dept> list = deptService.quertAll(new Dept());
        return Msg.success().add("depts",list);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("dept/{id}")
    public Dept selectOne(@PathVariable("id") Integer id) {
        return this.deptService.queryById(id);
    }

}