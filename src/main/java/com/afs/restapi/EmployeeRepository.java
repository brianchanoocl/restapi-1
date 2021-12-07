package com.afs.restapi;

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
}
