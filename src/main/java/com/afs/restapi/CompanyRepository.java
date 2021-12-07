package com.afs.restapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies= new ArrayList<>();

    public CompanyRepository(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Koby1",100 ,"male", 1000));
        employees.add(new Employee(2,"Koby2",200 ,"female", 2000));
        employees.add(new Employee(3,"Koby3", 18,"male", 9999999));
        List<Employee> employees2 = new ArrayList<>();
        employees2.add(new Employee(1,"Mary1",100 ,"male", 1000));
        employees2.add(new Employee(2,"Mary2",200 ,"female", 2000));
        employees2.add(new Employee(3,"Mary3", 18,"male", 9999999));
        Company abcCompany = new Company( 1,"ABC Company",employees);
        Company defCompany = new Company( 2,"DEF Company",employees2);
        companies.add(abcCompany);
        companies.add(defCompany);
    }

    public List<Company> findAll() {
        return companies;
    }

    public Company findById(Integer id) {
        return companies.stream()
                .filter(company -> Objects.equals(company.getId(), id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
