package com.shelley.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shelley.entity.Employee;
import com.shelley.global.Msg;
import com.shelley.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Employee)表控制层
 *
 * @author makejava
 * @since 2021-05-06 17:08:40
 */
@RestController
public class EmployeeController {
    /**
     * 服务对象
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 改造：
     * 让当前方法既可以批量删除，也可以单个删除
     * @param ids
     * @return
     */
    @DeleteMapping("emp/{ids}")
    public Msg deleteEmpById(@PathVariable String ids){
        // 批量删除
        if(ids.contains("-")){
            List<Integer> delIds = new ArrayList<>();
            String[] idsStr = ids.split("-");
            for (String s : idsStr) {
                delIds.add(Integer.parseInt(s));
            }
            employeeService.deleteEmpBatch(delIds);
        }else{
            // 单个删除
            Integer id = Integer.parseInt(ids);
            employeeService.deleteById(id);
        }

        return Msg.success();
    }
    /**
     * 路径中的属性参数，也可以将正常的参数对象属性的封装
     * id可以封装金employee对象
     * @param employee
     * @return
     */
    @PutMapping("emp/{id}")
    public Msg updateEmp(Employee employee){
        employeeService.update(employee);
        return Msg.success();
    }

    @PostMapping("checkUser")
    public Msg checkUser(String name){
        //先判断用户名是否是合法的格式
        String regex = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d·s]{2,20}$";
        if (!name.matches(regex)){
            return Msg.fail().add("va_msg","用户名格式不合法");
        }
        Employee employee = new Employee();
        employee.setName(name);
        List<Employee> employees = employeeService.queruAll(employee);
        boolean b = employees.isEmpty();
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名已存在");
        }
    }


    @PostMapping("emp")
    public Msg saveEmp(@Valid Employee employee , BindingResult result){
        if (result.hasErrors()){
            Map<String, Object> map = new HashMap<>();
            //检验失败，返回失败，在模态框中显示错误信息
            List<FieldError> errors= result.getFieldErrors();
            for (FieldError error : errors){
                map.put(error.getField(),error.getDefaultMessage());
            }
            return  Msg.fail().add("errorFileds",map);
        }else {
            employeeService.insert(employee);
            return Msg.success();
        }
    }

    @GetMapping("emps")
    public Msg getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn){
        PageHelper.startPage(pn,8);
        List<Employee> employees = employeeService.selectEmpWithDept();

        PageInfo<Employee> pageInfo = new PageInfo<>(employees,5);

        return Msg.success().add("pageInfo",pageInfo);
    }

    /**
     * 通过主键查询单条数据
     *
     * @PARAM ID 主键
     * @RETURN 单条数据
     */
    @GetMapping("emp/{id}")
    public Msg selectOne(@PathVariable("id") Integer id) {
        Employee employee = employeeService.queryById(id);
        return Msg.success().add("emp",employee);
    }
}