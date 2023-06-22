package net.javaguides.springboot.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Department;

import net.javaguides.springboot.repository.DeptRepository;
import net.javaguides.springboot.repository.EmployeeRepository;


@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	private DeptRepository deptRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//@Autowired
	//private BCryptPasswordEncoder passwordEncoder;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository= employeeRepository;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public List<Department> getAllDepartment() {
		return deptRepository.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}
	
	@Override
	public void saveDepartment(Department department) {
		this.deptRepository.save(department);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;
	}
	
	@Override
	public Department getDepartmentById(String deptName) {
		Optional<Department> optional = deptRepository.findById(deptName);
		Department department = null;
		if (optional.isPresent()) {
			department = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + deptName);
		}
		return department;
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}
	@Override
	public void deleteDepartmentById(String deptName) {
		this.deptRepository.deleteById(deptName);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}
	@Override
	public Employee save(UserDto registrationDto)
	{
		Employee employee=new Employee(registrationDto.getFirstName(),registrationDto.getLastName(), registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
		return employeeRepository.save(employee);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee=employeeRepository.findByEmail(username);
		if(employee==null){
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(), mapRolesToAuthorites(employee.getRoles()));
	}
	private Collection<? extends GrantedAuthority> mapRolesToAuthorites(Collection<Role> roles){
		return roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}