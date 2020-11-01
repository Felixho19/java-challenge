package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.controllers.request.CreateEmployeeRequest;
import jp.co.axa.apidemo.controllers.request.UpdateEmployeeRequest;
import jp.co.axa.apidemo.controllers.response.CreateEmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> retrieveEmployees();

    Employee getEmployee(Long employeeId);

    CreateEmployeeResponse createEmployee(CreateEmployeeRequest employee);

    void deleteEmployee(Long employeeId);

    void updateEmployee(Long currentEmployeeId, UpdateEmployeeRequest employee);
}