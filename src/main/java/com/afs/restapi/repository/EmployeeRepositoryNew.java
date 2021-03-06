package com.afs.restapi.repository;


import com.afs.restapi.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepositoryNew extends MongoRepository<Employee, String> {

    List<Employee> findAllByGender(String gender);

    List<Employee> findAllByCompanyId(String companyId);
}
