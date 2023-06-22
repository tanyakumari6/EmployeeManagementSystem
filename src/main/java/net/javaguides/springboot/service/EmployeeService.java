package net.javaguides.springboot.service;

import java.util.List;

import net.javaguides.springboot.model.UserDto;
import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface EmployeeService extends UserDetailsService {
	List<Employee> getAllEmployees();
	List<Department>getAllDepartment();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	Department getDepartmentById(String deptName);
	void deleteEmployeeById(long id);
	void deleteDepartmentById(String deptName);
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	void saveDepartment(Department department);
	Employee save(UserDto registrationDto);

}