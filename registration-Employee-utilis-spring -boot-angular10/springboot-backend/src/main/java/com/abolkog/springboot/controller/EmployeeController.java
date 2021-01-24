package com.abolkog.springboot.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abolkog.springboot.exception.ResourceNotFoundException;
import com.abolkog.springboot.model.Employee;
import com.abolkog.springboot.repository.EmployeeRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
  @Autowired
  private EmployeeRepository employeeRepository;
  
  @GetMapping("/employees")
  public ResponseEntity<List<Employee>>getAllEmployee(){
	  
	  List<Employee>result= employeeRepository.findAll();
	  return new ResponseEntity<>(result,HttpStatus.OK);
  }
  
  @PostMapping("/employees")
  public Employee createEmployeeList( @RequestBody Employee employee) {
	  return employeeRepository.save(employee);
	  
  }
  
  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee>  getEmployeeById(@PathVariable long id) {
	  Employee employee = employeeRepository.findById(id).orElseThrow(
			  ()->new ResourceNotFoundException("Employee not exist with this id :"+id));
	  return  ResponseEntity.ok(employee);
  }
  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee>updateEmployee(@PathVariable long id ,@RequestBody Employee employee){
	  Employee employeeData = employeeRepository.findById(id).orElseThrow(
			  ()->new ResourceNotFoundException("Employee not exist with this id :"+id));
	  employeeData.setFirstName(employee.getFirstName());
	  employeeData.setLastName(employee.getLastName());
	  employeeData.setEmailId(employee.getEmailId());
	  Employee emp =employeeRepository.save(employeeData);
	  return new ResponseEntity<>(emp,HttpStatus.CREATED);
	     
  }
  @DeleteMapping("employees/{id}")
  public ResponseEntity<Void>deleteEmployee(@PathVariable long id){
	  employeeRepository.deleteById(id);
	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
