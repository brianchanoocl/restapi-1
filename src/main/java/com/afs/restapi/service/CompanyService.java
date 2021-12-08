package com.afs.restapi.service;

import com.afs.restapi.repository.CompanyRepository;

public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
}
