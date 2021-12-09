package com.afs.restapi.mapper;

import com.afs.restapi.dto.CompanyRequest;
import com.afs.restapi.dto.CompanyResponse;
import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {
    private EmployeeMapper employeeMapper;

    public CompanyMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public Company toEntity(CompanyRequest companyRequest){
        Company company = new Company();

        company.setCompanyName(companyRequest.getName());

        return company;
    }

    public CompanyResponse toResponse(Company company, List<Employee> employees) {
        CompanyResponse companyResponse = new CompanyResponse();

        BeanUtils.copyProperties(company, companyResponse);
        companyResponse.setEmployees(
                employees.stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList())

        );

        return companyResponse;
    }
}
