package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.repository.CompanyRepository;

import java.util.List;

public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Integer id) {
        return companyRepository.findById(id);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company create(Company company) {
        return companyRepository.create(company);
    }

    public Company update(Integer id, Company company) {
        return companyRepository.update(id, company);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }
}
