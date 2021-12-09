package com.afs.restapi.controller;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryNew;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    CompanyRepositoryNew companyRepositoryNew;
    @Autowired
    EmployeeRepositoryNew employeeRepositoryNew;
    @Autowired
    MockMvc mockMvc;
    @BeforeEach
    void clearRepository(){
        companyRepository.clearAll();
        companyRepositoryNew.deleteAll();
    }



    @Test
    void should_return_companies_when_perform_get_given_companies() throws Exception {
        //given
        Employee employee = new Employee("1", "Brian", 18, "male", 9999,"1");
        Company company = new Company("1","Koby Company");
        companyRepositoryNew.insert(company);
        //When
        //then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("Koby Company"));
    }

    @Test
    void should_return_company_when_perform_get_given_companies_and_id() throws Exception {
        //given
        Company company = new Company("1","Koby Company");
        companyRepositoryNew.insert(company);

        //When
        //then
        mockMvc.perform(get("/companies/{id}", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("Koby Company"));
    }

    @Test
    void should_return_employees_when_perform_get_given_companies_and_id() throws Exception {
        //given
        Company company = new Company("2","Koby Company");
        companyRepository.create(company);

        //When
        //then
        mockMvc.perform(get("/companies/{id}/employees", company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Brian"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(9999));
    }

    @Test
    void should_return_employees_when_perform_get_given_companies_and_page_and_page_size() throws Exception {
        //given
        Company company = new Company("2","Koby Company");
        companyRepository.create(company);
        companyRepository.create(new Company("1", "dump"));

        //When
        //then
        mockMvc.perform(get("/companies").param("page", "1").param("pageSize", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("Koby Company"));
    }

    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        String company = "{\n" +
                "        \"id\": \"1\",\n" +
                "        \"companyName\": \"new\"\n" +
                "    }";

        //When
        //then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON).content(company))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("new"));
    }

    @Test
    void should_return_company_when_perform_put_given_company_and_id() throws Exception {
        //given
        Company company = new Company("2","Koby Company");
        companyRepository.create(company);

        String updatedCompany = "{\n" +
                "        \"companyName\": \"new\"\n" +
                "    }";

        //When
        //then
        mockMvc.perform(put("/companies/{id}", company.getId())
                .contentType(MediaType.APPLICATION_JSON).content(updatedCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("new"));
    }

    @Test
    void should_return_status_204_when_perform_delete_given_id() throws Exception {
        //given
        Company company = new Company("2","Koby Company");
        companyRepository.create(company);

        //When
        //then
        mockMvc.perform(delete("/companies/{id}", company.getId()))
                .andExpect(status().isNoContent());
    }
}
