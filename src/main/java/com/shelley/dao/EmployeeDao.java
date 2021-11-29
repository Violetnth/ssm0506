package com.shelley.dao;

import com.shelley.entity.Employee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Employee)表数据库访问层
 *
 * @author makejava
 * @since 2021-05-06 17:08:38
 */
public interface EmployeeDao {

    void deleteBatch(List<Integer> ids);

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
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Employee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param employee 实例对象
     * @return 对象列表
     */
    List<Employee> queryAll(Employee employee);

    /**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 影响行数
     */
    int insert(Employee employee);

    /**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 影响行数
     */
    int update(Employee employee);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}