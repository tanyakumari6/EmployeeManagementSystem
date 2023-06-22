package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.model.UserDto;
import net.javaguides.springboot.service.EmployeeService;


@Controller
@RequestMapping("/registration")
public class EmployeeRegistrationController {
    private EmployeeService employeeService;

    public EmployeeRegistrationController(EmployeeService employeeService) {
        super();
        this.employeeService = employeeService;
    }
    @ModelAttribute("employee")
    public UserDto userDto() {
        return new UserDto();
    }
    @GetMapping
    public String showRegistrationForm()
    {
        return "registration";
    }
    @PostMapping
    public String registerUserAccount(@ModelAttribute("employee") UserDto registrationDto) {
        employeeService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
