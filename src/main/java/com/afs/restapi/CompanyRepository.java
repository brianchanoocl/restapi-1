package com.afs.restapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companies= new ArrayList<>();

    public CompanyRepository(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Koby1",100 ,"male", 1000));
        employees.add(new Employee(2,"Koby2",200 ,"female", 2000));
        employees.add(new Employee(3,"Koby3", 18,"male", 9999999));
        List<Employee> employees2 = new ArrayList<>();
        employees.add(new Employee(1,"Koby1",100 ,"male", 1000));
        employees.add(new Employee(2,"Koby2",200 ,"female", 2000));
        employees.add(new Employee(3,"Koby3", 18,"male", 9999999));
        Company abcCompany = new Company( 1,"ABC Company",employees);
        Company defCompany = new Company( 1,"DEF Company",employees);
        companies.add(abcCompany);
        companies.add(defCompany);
    }
}
