package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.repository.DeptRepository;
//import net.javaguides.springboot.repository.EmployeeRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DeptRepository deptRepository;


	@Override
	public List<Department> getAllDepartment() {
		return deptRepository.findAll();
	}
	
	@Override
	public void saveDepartment(Department department) {
		this.deptRepository.save(department);
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
	public void deleteDepartmentById(String deptName) {
		this.deptRepository.deleteById(deptName);
	}

	
	@Override
	public Page<Department> findPaginatedDept(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.deptRepository.findAll(pageable);
	}
}