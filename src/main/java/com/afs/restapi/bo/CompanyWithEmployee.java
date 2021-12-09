package com.afs.restapi.bo;

import com.afs.restapi.dto.EmployeeResponse;
import com.afs.restapi.entity.Employee;

import java.util.List;

public class CompanyWithEmployee {
    private String id;
    private String name;
    private List<Employee> employees;
}
