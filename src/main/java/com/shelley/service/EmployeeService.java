package com.shelley.service;

import com.shelley.entity.Employee;
import java.util.List;

/**
 * (Employee)表服务接口
 *
 * @author makejava
 * @since 2021-05-06 17:08:38
 */
public interface EmployeeService {

    void deleteEmpBatch(List<Integer> list);
    /**
     * 根据条件查询employee
     * @param employee
     * @return
     */
    List<Employee> queruAll(Employee employee);

    /**
     * 查询员工的同时，Dept对象也是封装好了的
     * @return
     */
    List<Employee> selectEmpWithDept();

    /**
     * 通过ID查询对应的员工数据，带有部门信息
     * @param id
     * @return
     */
    Employee selectEmpWithDeptByPrimaryKey(Integer id);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Employee queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Employee> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    Employee insert(Employee employee);

    /**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    Employee update(Employee employee);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}