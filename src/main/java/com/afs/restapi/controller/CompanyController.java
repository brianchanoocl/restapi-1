package com.afs.restapi.controller;

import com.afs.restapi.dto.CompanyRequest;
import com.afs.restapi.dto.CompanyResponse;
import com.afs.restapi.dto.EmployeeResponse;
import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.mapper.CompanyMapper;
import com.afs.restapi.mapper.EmployeeMapper;
import com.afs.restapi.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyService companyService;
    private CompanyMapper companyMapper;
    private EmployeeMapper employeeMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper, EmployeeMapper employeeMapper){
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<Company> getAllCompanies(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable String id) {
        return companyMapper.toResponse(companyService.findById(id), companyService.findEmployeesByCompanyId(id));
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyId(@PathVariable String id) {
        return companyService.findEmployeesByCompanyId(id).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getEmployeesByPage(@RequestParam Integer page, Integer pageSize) {
        return companyService.findByPage(page, pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@RequestBody CompanyRequest companyRequest){
        return companyService.create(companyMapper.toEntity(companyRequest));
    }

    @PutMapping("/{id}")
    public  Company editCompany(@PathVariable String id, @RequestBody Company updatedCompany){
        Company company = companyService.findById(id);
        if(updatedCompany.getCompanyName() != null){
            company.setCompanyName(updatedCompany.getCompanyName());
        }
        return companyService.update(id,company);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id){
        Company company = companyService.findById(id);
        companyService.delete(company);
    }
}
