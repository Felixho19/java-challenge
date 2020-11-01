package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.controllers.request.CreateEmployeeRequest;
import jp.co.axa.apidemo.controllers.request.UpdateEmployeeRequest;
import jp.co.axa.apidemo.controllers.response.CreateEmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.IdNotExistException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Cacheable("employees")
    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long employeeId) {
        return findEmployeeById(employeeId);
    }

    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest employeeRequest){
        Employee newEmployee = Employee.builder()
                .department(employeeRequest.getDepartment())
                .name(employeeRequest.getName())
                .salary(employeeRequest.getSalary()).build();
        Employee createdEmployee =  employeeRepository.save(newEmployee);
        return CreateEmployeeResponse.builder()
                .id(createdEmployee.getId())
                .department(createdEmployee.getDepartment())
                .salary(createdEmployee.getSalary())
                .name(createdEmployee.getName())
                .build();
    }

    public void deleteEmployee(Long employeeId){
        findEmployeeById(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Long currentEmployeeId, UpdateEmployeeRequest updatedEmployee) {
        Employee currentEmp = findEmployeeById(currentEmployeeId);
        currentEmp.setName(updatedEmployee.getName());
        currentEmp.setDepartment(updatedEmployee.getDepartment());
        currentEmp.setSalary(updatedEmployee.getSalary());
        employeeRepository.save(currentEmp);
    }

    /**
     * Find employee by id, throw IdNotExistException if the id does not exists
     *
     * @param employeeId the employee id to be found
     * @return Employee with the employee id provided
     */
    @Cacheable("employee")
    private Employee findEmployeeById(Long employeeId){
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        if(!optEmp.isPresent()){
            throw new IdNotExistException();
        }
        return optEmp.get();
    }
}