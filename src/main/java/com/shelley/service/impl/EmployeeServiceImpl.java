package com.shelley.service.impl;

import com.shelley.dao.EmployeeDao;
import com.shelley.entity.Employee;
import com.shelley.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Employee)表服务实现类
 *
 * @author makejava
 * @since 2021-05-06 17:08:38
 */
@Service("employeeservice")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 批量删除要注意事务控制
     * @param list
     */
    @Override
    public void deleteEmpBatch(List<Integer> list) {
        // 不建议这么做，建议使用动态sql的in
        for (Integer integer : list) {
            employeeDao.deleteById(integer);
        }
    }

    @Override
    public List<Employee> queruAll(Employee employee) {
        return employeeDao.queryAll(employee);
    }

    @Override
    public List<Employee> selectEmpWithDept() {
        return employeeDao.selectEmpWithDept();
    }

    @Override
    public Employee selectEmpWithDeptByPrimaryKey(Integer id) {
        return employeeDao.selectEmpWithDeptByPrimaryKey(id);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Employee queryById(Integer id) {
        return this.employeeDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Employee> queryAllByLimit(int offset, int limit) {
        return this.employeeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    @Override
    public Employee insert(Employee employee) {
        this.employeeDao.insert(employee);
        return employee;
    }

    /**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    @Override
    public Employee update(Employee employee) {
        this.employeeDao.update(employee);
        return this.queryById(employee.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.employeeDao.deleteById(id) > 0;
    }
}