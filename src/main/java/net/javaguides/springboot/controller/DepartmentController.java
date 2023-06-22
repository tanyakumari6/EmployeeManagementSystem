package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	// display list of employees
	@GetMapping("/dept")
	public String viewHomePage(Model model) {
		model.addAttribute("listDepartment",departmentService.getAllDepartment());
		return "new_department";
	} 
	
	@GetMapping("/showNewDepartmentForm")
	public String showNewDepartmentForm(Model model) {
		// create model attribute to bind form data
		Department department = new Department();
		model.addAttribute("department", department);
		return "add_department";
	}
	
	@PostMapping("/saveDepartment")
	public String saveDepartment(@ModelAttribute("department") Department department) {
		// save employee to database
		departmentService.saveDepartment(department);
		return "redirect:/dept";
	}
	
	@GetMapping("/showFormForUpdateDept/{deptName}")
	public String showFormForUpdate(@PathVariable ( value = "deptName") String deptName, Model model) {
		
		// get employee from the service
		Department department = departmentService.getDepartmentById(deptName);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("department", department);
		return "update_department";
	}
	
	@GetMapping("/deleteDepartment/{deptName}")
	public String deleteDepartment(@PathVariable (value = "deptName") String deptName) {
		
		// call delete employee method 
		this.departmentService.deleteDepartmentById(deptName);
		return "redirect:/";
	}
	
	
	@GetMapping("/deptpage/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		Page<Department> page = departmentService.findPaginatedDept(pageNo, pageSize, sortField, sortDir);
		List<Department> listDepartment= page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listDepartment", listDepartment);
		return "index";
	}
}
