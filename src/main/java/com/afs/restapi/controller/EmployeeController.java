package com.afs.restapi.controller;

import com.afs.restapi.dto.EmployeeRequest;
import com.afs.restapi.dto.EmployeeResponse;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.mapper.EmployeeMapper;
import com.afs.restapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.findAll().stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable String id) {
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam String gender) {
        return employeeService.findByGender(gender);
    }
    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam Integer page, Integer pageSize) {
        return employeeService.findByPage(page, pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.create(employeeMapper.toEntity(employeeRequest));
    }

    @PutMapping("/{id}")
    public  Employee editEmployee(@PathVariable String id, @RequestBody EmployeeRequest employeeRequest){
        return employeeService.edit(id,employeeMapper.toEntity(employeeRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id){
        employeeService.delete(id);
    }

}
