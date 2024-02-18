package org.example.controllers;

import org.example.NoVacancyForDirectorException;
import org.example.models.Director;
import org.example.models.Employee;
import org.example.service.DirectorService;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
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
    @GetMapping("/new")
    public String newEmployee(Model model, @ModelAttribute("director") Director director, RedirectAttributes redirectAttributes) {
        List<Employee> employees = employeeService.getAllEmployee().stream().filter(employee -> employee.getIdDirector() == 0).collect(Collectors.toList());
        model.addAttribute("employees", employees);
        model.addAttribute("director", (Director) model.getAttribute("director") );
        return "employees/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("employee") Employee employee, @ModelAttribute("director") Director director,BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws NoVacancyForDirectorException {
        employee.setIdDirector(directorService.getNewId());
        employeeService.saveEmployee(employee);
        return "redirect:directors/new";
    }
    @PostMapping("set-director")
    public String setDirector(@RequestParam int employeeId,
                              @RequestParam int directorId, Model model) {
        employeeService.setDirectorId(employeeId, directorId);
       // directorService.getDirectorById(directorId).
        //TODO: обновить директора
        System.out.println(employeeService.getAllEmployee());
        return "redirect:/employees/new";
    }

    @PostMapping("remove-director")
    public String removeDirector(@RequestParam int employeeId) {
        employeeService.removeDirectorId(employeeId);
        //TODO: обновить директора
        System.out.println(employeeService.getAllEmployee());
        return "redirect:/employees/new";
    }

}
