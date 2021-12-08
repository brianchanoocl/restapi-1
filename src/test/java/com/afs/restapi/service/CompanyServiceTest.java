package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = Stream.of(new Company(1,"company",null))
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
        Company company = new Company(1,"company",null);
        given(companyRepository.findById(1))
                .willReturn(company);
        //when
        Company actual =  companyRepository.findById(company.getId());
        //then
        assertEquals(company, actual);
    }

    
}
