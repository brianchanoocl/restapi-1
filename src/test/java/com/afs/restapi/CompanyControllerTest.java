package com.afs.restapi;

import com.afs.restapi.objects.Company;
import com.afs.restapi.objects.Employee;
import com.afs.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MockMvc mockMvc;
    @BeforeEach
    void clearRepository(){
        companyRepository.clearAll();
    }

    @Test
    void should_return_companies_when_perform_get_given_companies() throws Exception {
        //given
        Employee employee = new Employee(1, "Brian", 18, "male", 9999);
        Company company = new Company(1,"Koby Company", Stream.of(employee).collect(Collectors.toList()));
        companyRepository.create(company);
        //When
        //then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("Koby Company"))
                .andExpect(jsonPath("$[0].employees" ,hasSize(1)))
                .andExpect(jsonPath("$[0].employees[0].name").value("Brian"))
                .andExpect(jsonPath("$[0].employees[0].age").value(18))
                .andExpect(jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(jsonPath("$[0].employees[0].salary").value(9999));
    }
}
