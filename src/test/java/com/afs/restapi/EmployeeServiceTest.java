package com.afs.restapi;

import com.afs.restapi.objects.Employee;
import com.afs.restapi.repository.EmployeeRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = Stream.of(new Employee(1,"Koby",3,"male",2))
                .collect(Collectors.toList());
        given(employeeRepository.findAll())
                .willReturn(employees);
        //when
        List<Employee> actual =  employeeService.findAll();
        //then
        assertEquals(employees,actual);
    }

    @Test
    void should_return_updated_when_edit_employee_given_updated_employee() {
        //given

        //When
        Employee employee = new Employee(1,"Koby",20,"male",5);
        Employee updatedEmployee = new Employee(1,"Koby",20,"male",999);
        given(employeeRepository.findById(any()))
                .willReturn(employee);
        employee.setAge(updatedEmployee.getSalary());
        employee.setSalary(updatedEmployee.getSalary());
        given(employeeRepository.update(any(),any(Employee.class)))
                .willReturn(employee);
        Employee actual = employeeService.edit(employee.getId(),updatedEmployee);
        //then
        assertEquals(actual,employee);
    }
}


//Controller
//Service
//Repository
