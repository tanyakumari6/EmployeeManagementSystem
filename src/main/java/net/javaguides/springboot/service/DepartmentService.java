package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Department;

public interface DepartmentService {
	List<Department>getAllDepartment();
	Department getDepartmentById(String deptName);
	void deleteDepartmentById(String deptName);
	Page<Department> findPaginatedDept(int pageNo, int pageSize, String sortField, String sortDirection);
	void saveDepartment(Department department);
}