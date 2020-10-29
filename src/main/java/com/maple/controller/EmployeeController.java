package com.maple.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maple.dao.EmployeeDao;
import com.maple.model.Employee;
 
/**
 * 
 * @Description: 
 * @author: uwank171 
 * @date: 2020年10月28日 下午2:07:29 
 *
 */
@RestController
@RequestMapping("es")
public class EmployeeController {
 
    @Autowired
    private EmployeeDao employeeDao;
 
    /**
     * 添加
     * @return
     */
    @GetMapping("/add")
    public String add() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("xuxu");
        employee.setLastName("zh");
        employee.setAge(26);
        employee.setAbout("i am in peking");
        employeeDao.save(employee);
        System.err.println("add a obj");
        return "success";
    }
 
    /**
     * 删除
     * @return
     */
    @GetMapping("delete")
    public String delete() {
        Employee employee = employeeDao.queryEmployeeById("1");
        employeeDao.delete(employee);
        System.err.println("delete a obj");
        return "success";
    }
 
    /**
     * 局部更新
     * @return
     */
    @GetMapping("update")
    public String update() {
        Employee employee = employeeDao.queryEmployeeById("1");
        employee.setFirstName("哈哈");
        employeeDao.save(employee);
        System.err.println("update a obj");
        return "success";
    }
    /**
     * 查询
     * @return
     */
    @GetMapping("query")
    public Employee query() {
        Employee accountInfo = employeeDao.queryEmployeeById("1");
        System.err.println(accountInfo.toString());
        return accountInfo;
    }
}  