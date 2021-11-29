package com.shelley.service;

import com.shelley.entity.Dept;
import java.util.List;

/**
 * (Dept)表服务接口
 *
 * @author makejava
 * @since 2021-05-06 17:08:59
 */
public interface DeptService {

    /**
     * 查询部门
     * @param dept
     * @return
     */
    List<Dept> quertAll(Dept dept);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dept queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Dept> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    Dept insert(Dept dept);

    /**
     * 修改数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    Dept update(Dept dept);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}