package com.example.rest.controller;

import com.example.rest.entity.Employee;
import com.example.rest.exception.EmployeeNotFoundException;
import com.example.rest.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepository.class);

    @GetMapping("/employees")
    public List<Employee> getAll() {
        log.info("get All Employees");

        return repository.findAll();
    }

    @PostMapping("/employees")
    public Employee addNewEmploye(@RequestBody Employee newEmployee) {
        log.info("add new Employee " + newEmployee);

        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    public Employee getOne(@PathVariable Long id) {
        log.info("get Employee " + id);

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        log.info("update Employee" + id);

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        log.warn("delete Employee " + id);

        repository.deleteById(id);
    }
}
