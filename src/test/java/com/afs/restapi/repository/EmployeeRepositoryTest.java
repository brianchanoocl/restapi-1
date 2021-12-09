package com.afs.restapi.repository;

import com.afs.restapi.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class EmployeeRepositoryTest {
    @InjectMocks
    @Spy
    EmployeeRepository employeeRepository;

    @BeforeEach
    void clearRepository(){
        employeeRepository.clearAll();
    }

    @Test
    void should_return_Employees_when_find_all_given_companies() {
        //given
        //Employee
        //employeeRepository.create()

        //when

        //then
    }

}
