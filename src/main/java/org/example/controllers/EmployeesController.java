package org.example.controllers;

import org.example.NoVacancyForDirectorException;
import org.example.models.Director;
import org.example.models.Employee;
import org.example.models.enums.Department;
import org.example.service.DirectorService;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employees")
public class EmployeesController {
    private final EmployeeService employeeService;
    private final DirectorService directorService;

    public EmployeesController(EmployeeService employeeService, DirectorService directorService) {
        this.employeeService = employeeService;
        this.directorService = directorService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployee());
        return "employees/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "directors/show";
    }

    @GetMapping("/add-to-director")
    public String addToDirector(Model model, @ModelAttribute("director") Director director) {
        List<Employee> employees = employeeService.getAllEmployee().stream().filter(employee -> employee.getIdDirector() == 0).collect(Collectors.toList());
        if (employees.isEmpty()) {
            return "error";
        }
        model.addAttribute("employees", employees);
        model.addAttribute("director", (Director) model.getAttribute("director"));
        model.addAttribute("error", model.getAttribute("bindingResult"));
        return "employees/add-to-director";
    }

    @GetMapping("/new")
    public String createEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        model.addAttribute("directors", directorService.getDirectorDepartmentMap());
        return "employees/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("employee") Employee employee, @ModelAttribute("director") Director director, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws NoVacancyForDirectorException {
        if (bindingResult.hasFieldErrors("name")) {
            return "directors/new";
        }
        employeeService.saveEmployee(employee);
        return "employees/show";
    }

    @PostMapping("set-director")
    public String setDirector(@RequestParam int employeeId,
                              @RequestParam int directorId) {
        employeeService.setDirectorId(employeeId, directorId);
        return "redirect:/employees/new";
    }

    @PostMapping("remove-director")
    public String removeDirector(@RequestParam int employeeId) {
        employeeService.removeDirectorId(employeeId);
        return "redirect:/employees/new";
    }
}
