package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoEmployeeFoundException;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.repository.EmployeeRepositoryNew;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    EmployeeRepositoryNew employeeRepositoryNew;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = Stream.of(new Employee("1","Koby",3,"male",2,"1"))
                .collect(Collectors.toList());
        given(employeeRepositoryNew.findAll())
                .willReturn(employees);
        //when
        List<Employee> actual =  employeeService.findAll();
        //then
        assertEquals(employees,actual);
    }

    @Test
    void should_return_updated_when_edit_employee_given_updated_employee() {
        //given
        Employee employee = new Employee("1","Koby",20,"male",5,"1");
        Employee updatedEmployee = new Employee("1","Koby",99,"male",6,"1");
        given(employeeRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(employee));
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        given(employeeRepositoryNew.save(any(Employee.class)))
                .willReturn(employee);
        //When
        Employee actual = employeeService.edit(employee.getId(),updatedEmployee);
        //then
        verify(employeeRepositoryNew).save(employee);
        assertEquals(actual,employee);
    }

    @Test
    void should_return_employee_when_create_employee_given_new_employee() {
        //given
        Employee employee = new Employee("1","Koby",20,"male",5,"1");
        given(employeeRepositoryNew.insert(any(Employee.class)))
                .willReturn(employee);
        //When
        Employee actual = employeeService.create(employee);
        //then
        assertEquals(actual,employee);
    }

    @Test
    void should_return_employee_when_find_by_id_given_employees_and_id() {
        //given
        Employee employee = new Employee("1","Koby",3,"male",2,"1");
        given(employeeRepositoryNew.findById(any()))
                .willReturn(java.util.Optional.of(employee));

        //When
        Employee actual = employeeService.findById(employee.getId());

        //then
        assertEquals(actual, employee);
    }

    @Test
    void should_return_employees_when_find_by_gender_given_employees_and_gender() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1","Koby",3,"male",2,"1"));
        given(employeeRepositoryNew.findAllByGender("male"))
                .willReturn(employees);
        //When
        List<Employee> actual = employeeService.findByGender("male");
        //then
        verify(employeeRepositoryNew).findAllByGender("male");
        assertEquals(actual, employees);
    }

    @Test
    void should_return_employees_when_find_by_page_and_pageSize_given_employees_and_page_and_pageSize() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1","Koby",3,"male",2,"1"));
        employees.add(new Employee("2","Koby",3,"male",2,"1"));
        given(employeeRepositoryNew.findAll(PageRequest.of(1, 2)))
                .willReturn(new PageImpl<>(employees, PageRequest.of(1, 2), 2));

        //When
        List<Employee> actual = employeeService.findByPage(1,2);
        //then
        verify(employeeRepositoryNew).findAll(PageRequest.of(1, 2));
        assertEquals(actual,employees);

    }

    @Test
    void should_remove_employee_when_delete_given_id() {
        //given
        Employee employee = new Employee("1","Koby",3,"male",2,"1");

        //When
        employeeService.delete(employee.getId());
        //then
        verify(employeeRepositoryNew).deleteById(employee.getId());

    }

    @Test
    void should_throw_exception_when_getEmployeeByID_given_employees_and_invalid_id() {
        //given
        String id = "1";
        Employee employee = new Employee("1","people",18, "male", 10, "1");
        //when
        given(employeeRepositoryNew.findById("1"))
                .willThrow(NoEmployeeFoundException.class);

        //then
        assertThrows(NoEmployeeFoundException.class, () -> employeeService.findById(id));
    }
}