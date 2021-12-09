package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return  employeeRepository.findAll();
    }

    public Employee edit(String id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id);
        if(updatedEmployee.getAge() != null){
            employee.setAge(updatedEmployee.getAge());
        }
        if(updatedEmployee.getSalary() != null){
            employee.setSalary(updatedEmployee.getSalary());
        }
        return employeeRepository.update(id,employee);
    }

    public Employee create(Employee employee) {
        return employeeRepository.create(employee);
    }

    public Employee findById(String id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeRepository.findByPage(page,pageSize);
    }

    public void delete(String id) {
        Employee employee = employeeRepository.findById(id);
        employeeRepository.delete(employee);
    }
}
