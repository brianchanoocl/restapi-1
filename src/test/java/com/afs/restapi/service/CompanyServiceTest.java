package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryNew;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @Mock
    CompanyRepositoryNew companyRepositoryNew;
    @Mock
    EmployeeRepositoryNew employeeRepositoryNew;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = Stream.of(new Company("1","company"))
                .collect(Collectors.toList());
        given(companyRepositoryNew.findAll())
                .willReturn(companies);
        //when
        List<Company> actual =  companyService.findAll();
        //then
        assertEquals(companies,actual);
    }

    @Test
    void should_return_company_when_find_company_by_id_given_companies_and_id() {
        //given
        Company company = new Company("1","company");
        given(companyRepositoryNew.findById("1"))
                .willReturn(java.util.Optional.of(company));
        //when
        Company actual =  companyService.findById(company.getId());
        //then
        assertEquals(company, actual);
    }

    @Test
    void should_return_employees_when_find_Employees_By_Company_Id_given_companies_and_id() {
        //given
        List<Employee> employees = Stream.of(new Employee("1","Koby",3,"male",2,"1"))
                .collect(Collectors.toList());
        Company company = new Company("1","company");
        given(employeeRepositoryNew.findAllByCompanyId("1"))
                .willReturn(employees);
        //when
        List<Employee> actual =  companyService.findEmployeesByCompanyId(company.getId());
        //then
        assertEquals(employees,actual);
    }

    @Test
    void should_return_companies_when_find_company_by_page_given_companies_and_page_and_pageSize() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("1","company"));
        companies.add(new Company("2","company"));
        given(companyRepositoryNew.findAll(PageRequest.of(1, 2)))
                .willReturn(new PageImpl<>(companies, PageRequest.of(1, 2), 2));
        //when
        List<Company> actual =  companyService.findByPage(1, 2);
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_company_when_create_company_given_company() {
        //given
        Company company = new Company("1","company");
        given(companyRepositoryNew.insert(company))
                .willReturn(company);
        //when
        Company actual =  companyService.create(company);
        //then
        assertEquals(company,actual);
    }

    @Test
    void should_return_company_when_update_company_given_updated_comapny() {
        //given
        Company company = new Company("1","company");
        Company updatedCompany = new Company("1","new");
        given(companyRepository.findById(company.getId()))
                .willReturn(company);
        company.setCompanyName(updatedCompany.getCompanyName());
        given(companyRepository.update("1", updatedCompany))
                .willReturn(company);
        //when
        Company actual =  companyService.update(company.getId(), updatedCompany);
        //then
        assertEquals(company,actual);
    }

    @Test
    void should_remove_company_when_delete_given_updated_company() {
        //given
        Company company = new Company("1","company");
        given(companyRepository.findById("1"))
                .willReturn(company);
        //when
        companyService.delete(company);
        //then
        verify(companyRepository).delete(company);
    }

    @Test
    void should_throw_exception_when_getCompanyByID_given_companies_and_invalid_id() {
        //given
        String id = "1";
        Company company = new Company("1","company");
        //when
        given(companyRepository.findById("1"))
                .willThrow(NoCompanyFoundException.class);

        //then
        assertThrows(NoCompanyFoundException.class, () -> companyService.findById(id));
    }

}
