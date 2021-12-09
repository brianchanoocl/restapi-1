package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryNew;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EmployeeRepositoryNew employeeRepositoryNew;
    private CompanyRepositoryNew companyRepositoryNew;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepositoryNew employeeRepositoryNew, CompanyRepositoryNew companyRepositoryNew) {
        this.companyRepository = companyRepository;
        this.employeeRepositoryNew = employeeRepositoryNew;
        this.companyRepositoryNew = companyRepositoryNew;
    }

    public List<Company> findAll() {
        return companyRepositoryNew.findAll();
    }

    public Company findById(String id) {
        return companyRepositoryNew.findById(id).orElseThrow(NoCompanyFoundException::new);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyRepositoryNew.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company create(Company company) {
        return companyRepositoryNew.insert(company);
    }

    public Company update(String id, Company company) {
        return companyRepositoryNew.save(company);
    }

    public void delete(Company company) {
        companyRepositoryNew.delete(company);
    }

    public List<Employee> findEmployeesByCompanyId(String id) {
        return employeeRepositoryNew.findAllByCompanyId(id);
    }
}
