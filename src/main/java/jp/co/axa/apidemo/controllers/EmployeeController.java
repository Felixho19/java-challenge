package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.controllers.request.CreateEmployeeRequest;
import jp.co.axa.apidemo.controllers.request.UpdateEmployeeRequest;
import jp.co.axa.apidemo.controllers.response.CreateEmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for get/create/delete/update employee(s)
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * get all Employee
     *
     */
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    /**
     * find an Employee
     *
     * @param employeeId ID of the Employee to find
     */
    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    /**
     * create a new Employee.
     *
     * @param createEmployeeRequest the employee to be created
     */
    @PostMapping("/employee")
    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        CreateEmployeeResponse createdEmployee = employeeService.createEmployee(createEmployeeRequest);
        log.info("Employee create Successfully: " + createdEmployee.toString());
        return createdEmployee;
    }

    /**
     * delete an Employee
     *
     * @param employeeId delete a employee by id
     */
    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        log.info("Employee Deleted Successfully: id = " + employeeId);
    }

    /**
     * update an Employee
     *
     * @param updateEmployeeRequest the employee to be updated
     * @param employeeId the id of the existing employee to be updated
     */
    @PutMapping("/employee/{employeeId}")
    public void updateEmployee(@RequestBody UpdateEmployeeRequest updateEmployeeRequest,
                               @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.updateEmployee(employeeId, updateEmployeeRequest);
        log.info("Employee Updated Successfully");
    }

}
