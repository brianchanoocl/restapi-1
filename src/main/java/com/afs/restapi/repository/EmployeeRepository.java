package com.afs.restapi.repository;

import com.afs.restapi.objects.Employee;
import com.afs.restapi.exception.NoEmployeeFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(1,"Koby",120 ,"male", 1000));
        employees.add(new Employee(2,"Mary",3 ,"female", 2000));
        employees.add(new Employee(3,"Brian", 18,"male", 9999999));
    }

    public List<Employee> findAll(){
        return employees;
    }

    public Employee findById(Integer id) {
        return employees.stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst()
                .orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> Objects.equals(employee.getGender(), gender))
                .collect(Collectors.toList());
    }
    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employees.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee create(Employee employee) {
        employee.setId(employees.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0)+1
        );
        employees.add(employee);
        return employee;
    }

    public Employee update(Integer id, Employee updatedEmployee) {
        Employee employee = findById(id);
        employees.remove(employee);
        employees.add(updatedEmployee);
        return updatedEmployee;
    }

    public void delete(Employee employee) {
        employees.remove(employee);
    }

    public void clearAll() {
        employees.clear();
    }
}
