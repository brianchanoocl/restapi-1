package com.afs.restapi.controller;

import com.afs.restapi.objects.Company;
import com.afs.restapi.objects.Employee;
import com.afs.restapi.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployeeById(@PathVariable Integer id) {
        return companyRepository.findById(id).getEmployees();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getEmployeesByPage(@RequestParam Integer page, Integer pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@RequestBody Company company){
        return companyRepository.create(company);
    }

    @PutMapping("/{id}")
    public  Company editCompany(@PathVariable Integer id,@RequestBody Company updatedCompany){
        Company company = companyRepository.findById(id);
        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }
        return companyRepository.update(id,company);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Company deleteCompany(@PathVariable Integer id){
        Company company = companyRepository.findById(id);
        return companyRepository.delete(company);
    }
}
