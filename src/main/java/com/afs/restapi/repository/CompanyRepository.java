package com.afs.restapi.repository;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies= new ArrayList<>();

    public CompanyRepository(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Koby1",100 ,"male", 1000,1));
        employees.add(new Employee(2,"Koby2",200 ,"female", 2000,1));
        employees.add(new Employee(3,"Koby3", 18,"male", 9999999,1));
        List<Employee> employees2 = new ArrayList<>();
        employees2.add(new Employee(1,"Mary1",100 ,"male", 1000,2));
        employees2.add(new Employee(2,"Mary2",200 ,"female", 2000,2));
        employees2.add(new Employee(3,"Mary3", 18,"male", 9999999,2));
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
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        company.setId(companies.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0)+1
        );
        companies.add(company);
        return company;
    }

    public Company update(Integer id, Company updatedCompany) {
        Company company = findById(id);
        companies.remove(company);
        companies.add(updatedCompany);
        return updatedCompany;
    }

    public void delete(Company company){
        companies.remove(company);
    }

    public void clearAll() {
        companies.clear();
    }
}
