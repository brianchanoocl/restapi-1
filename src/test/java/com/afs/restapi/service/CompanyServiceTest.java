package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import com.afs.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = Stream.of(new Company("1","company",null))
                .collect(Collectors.toList());
        given(companyRepository.findAll())
                .willReturn(companies);
        //when
        List<Company> actual =  companyRepository.findAll();
        //then
        assertEquals(companies,actual);
    }

    @Test
    void should_return_company_when_find_company_by_id_given_companies_and_id() {
        //given
        Company company = new Company("1","company",null);
        given(companyRepository.findById("1"))
                .willReturn(company);
        //when
        Company actual =  companyRepository.findById(company.getId());
        //then
        assertEquals(company, actual);
    }

    @Test
    void should_return_employees_when_find_Employees_By_Company_Id_given_companies_and_id() {
        //given
        List<Employee> employees = Stream.of(new Employee("1","Koby",3,"male",2,"1"))
                .collect(Collectors.toList());
        Company company = new Company("1","company",employees);
        given(companyRepository.findEmployeesByCompanyId("1"))
                .willReturn(employees);
        //when
        List<Employee> actual =  companyRepository.findEmployeesByCompanyId(company.getId());
        //then
        assertEquals(employees,actual);
    }

    @Test
    void should_return_companies_when_find_company_by_page_given_companies_and_page_and_pageSize() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("1","company",null));
        companies.add(new Company("2","company",null));
        companies.add(new Company("1","company",null));
        given(companyRepository.findByPage(1, 3))
                .willReturn(companies);
        //when
        List<Company> actual =  companyRepository.findByPage(1, 3);
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_company_when_create_company_given_comapny() {
        //given
        Company company = new Company("1","company",null);
        given(companyRepository.create(company))
                .willReturn(company);
        //when
        Company actual =  companyRepository.create(company);
        //then
        assertEquals(company,actual);
    }

    @Test
    void should_return_company_when_update_company_given_updated_comapny() {
        //given
        Company company = new Company("1","company",null);
        Company updatedCompany = new Company("1","new",null);
        given(companyRepository.findById(company.getId()))
                .willReturn(company);
        company.setCompanyName(updatedCompany.getCompanyName());
        given(companyRepository.update("1", updatedCompany))
                .willReturn(company);
        //when
        Company actual =  companyRepository.update(company.getId(), updatedCompany);
        //then
        assertEquals(company,actual);
    }

    @Test
    void should_remove_company_when_delete_given_updated_company() {
        //given
        Company company = new Company("1","company",null);
        given(companyRepository.findById("1"))
                .willReturn(company);
        //when
        companyRepository.delete(company);
        //then
        verify(companyRepository).delete(company);
    }

    @Test
    void should_throw_exception_when_getCompanyByID_given_companies_and_invalid_id() {
        //given
        String id = "1";
        Company company = new Company("1","company",null);
        //when
        given(companyRepository.findById("1"))
                .willThrow(NoCompanyFoundException.class);

        //then
        assertThrows(NoCompanyFoundException.class, () -> companyService.findById(id));
    }

}
