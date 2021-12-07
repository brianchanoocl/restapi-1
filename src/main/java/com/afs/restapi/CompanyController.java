package com.afs.restapi;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id) {
        return companyRepository.findById(id);
    }
    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getEmployeesByPage(@RequestParam Integer page, Integer pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }
}
