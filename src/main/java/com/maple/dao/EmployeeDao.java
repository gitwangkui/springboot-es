package com.maple.dao;
 
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.maple.model.Employee;
/**
 * 
 * @Description: 
 * @author: uwank171 
 * @date: 2020年10月28日 下午2:06:07 
 *
 */
public interface EmployeeDao extends ElasticsearchRepository<Employee,String>{
 
    /**
     * 查询雇员信息
     * @param id
     * @return
     */
    Employee queryEmployeeById(String id);
    
}