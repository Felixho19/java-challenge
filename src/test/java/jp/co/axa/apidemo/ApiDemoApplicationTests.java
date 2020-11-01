package jp.co.axa.apidemo;

import jp.co.axa.apidemo.controllers.request.CreateEmployeeRequest;
import jp.co.axa.apidemo.controllers.request.UpdateEmployeeRequest;
import jp.co.axa.apidemo.controllers.response.CreateEmployeeResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.IdNotExistException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { ApiDemoApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApiDemoApplicationTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void createEmployee() {
        EmployeeService service = new EmployeeServiceImpl(employeeRepository);
        CreateEmployeeResponse res =  createTestEmployee(service);
        CreateEmployeeRequest employee = getTestCreateEmployeeRequest();
        Assert.assertEquals(employee.getName(), res.getName());
        Assert.assertEquals(employee.getDepartment(), res.getDepartment());
        Assert.assertEquals(employee.getSalary(), res.getSalary());
        Assert.assertNotNull(res.getId());
    }

    @Test
    @Transactional
    public void getAllEmployee(){
        EmployeeService service = new EmployeeServiceImpl(employeeRepository);

        int prevNumberOfEmployees = service.retrieveEmployees().size();

        // create at least one employee
        createTestEmployee(service);
        // check if the totoal number of employees increased by 1
        List<Employee> employees = service.retrieveEmployees();
        Assert.assertEquals(prevNumberOfEmployees + 1, employees.size());

        // test if null value exists
        Employee firstEmployee = employees.get(0);
        Assert.assertNotNull(firstEmployee.getName());
        Assert.assertNotNull(firstEmployee.getDepartment());
        Assert.assertNotNull(firstEmployee.getSalary());
        Assert.assertNotNull(firstEmployee.getId());
    }

    @Test
    public void updateEmployee() {
        EmployeeService service = new EmployeeServiceImpl(employeeRepository);

        // create employee
        CreateEmployeeResponse createdEmployeeResponse = createTestEmployee(service);

        // update the created employee
        UpdateEmployeeRequest updateEmployeeRequest = getTestUpdateEmployeeRequest();
        service.updateEmployee(createdEmployeeResponse.getId(), updateEmployeeRequest);

        // check if all values updated
        Employee updatedEmployee = service.getEmployee(createdEmployeeResponse.getId());
        Assert.assertEquals(updateEmployeeRequest.getName(), updatedEmployee.getName());
        Assert.assertEquals(updateEmployeeRequest.getDepartment(), updatedEmployee.getDepartment());
        Assert.assertEquals(updateEmployeeRequest.getSalary(), updatedEmployee.getSalary());
        Assert.assertEquals(createdEmployeeResponse.getId(), updatedEmployee.getId());
    }

    @Test
    @Transactional
    public void deleteEmployee(){
        EmployeeService service = new EmployeeServiceImpl(employeeRepository);

        // create at least 1 employee
        Long employeeIdToDelete = createTestEmployee(service).getId();

        List<Employee> employees = service.retrieveEmployees();
        int prevNumberOfEmployees = employees.size();

        service.deleteEmployee(employeeIdToDelete);

        Assert.assertEquals(prevNumberOfEmployees - 1, service.retrieveEmployees().size());
    }

    @Test(expected = IdNotExistException.class)
    @Transactional
    public void deleteNonExistingEmployee(){
        EmployeeService service = new EmployeeServiceImpl(employeeRepository);
        Long employeeIdToDelete = (long) (service.retrieveEmployees().size() + 1);
        service.deleteEmployee(employeeIdToDelete);
    }

    @Test(expected = IdNotExistException.class)
    @Transactional
    public void findNonExistingEmployee(){
        EmployeeService service = new EmployeeServiceImpl(employeeRepository);
        Long employeeIdToDelete = (long) (service.retrieveEmployees().size() + 1);
        service.getEmployee(employeeIdToDelete);
    }

    @Test(expected = IdNotExistException.class)
    @Transactional
    public void updateNonExistingEmployee(){
        EmployeeService service = new EmployeeServiceImpl(employeeRepository);
        Long employeeIdToDelete = (long) (service.retrieveEmployees().size() + 1);
        service.updateEmployee(employeeIdToDelete, getTestUpdateEmployeeRequest());
    }

    private CreateEmployeeResponse createTestEmployee(EmployeeService service){
        // create employee
        CreateEmployeeRequest employee = getTestCreateEmployeeRequest();
        return service.createEmployee(employee);
    }

    private CreateEmployeeRequest getTestCreateEmployeeRequest(){
        return CreateEmployeeRequest.builder().name("Eric").salary(500).department("travel").build();
    }

    private UpdateEmployeeRequest getTestUpdateEmployeeRequest(){
        return UpdateEmployeeRequest.builder().name("Eric Lau").salary(2000).department("data science").build();
    }
}
