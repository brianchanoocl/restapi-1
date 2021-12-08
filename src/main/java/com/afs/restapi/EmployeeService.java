package com.afs.restapi;

import com.afs.restapi.objects.Employee;
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

    public Employee edit(Integer id, Employee updatedEmployee) {
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

    public Employee findById(Integer id) {
        return employeeRepository.findById(id);
    }
}
