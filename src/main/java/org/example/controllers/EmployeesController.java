package org.example.controllers;

import org.example.exception.CustomException;
import org.example.models.Director;
import org.example.models.Employee;
import org.example.service.DirectorService;
import org.example.service.EmployeeService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Controller
@RequestMapping("/employees")
public class EmployeesController {
    private static final Logger LOGGER = Logger.getLogger(DirectorsController.class);

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
    public String show(@PathVariable("id") int id, Model model) throws CustomException {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("directors", directorService.getDirectorDepartmentMap());
        return "employees/show";
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
    public String create(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult, @ModelAttribute("director") Director director, Model model) {
        if (bindingResult.hasFieldErrors("name")) {
            model.addAttribute("directors", directorService.getDirectorDepartmentMap());
            return "employees/new";
        }
        employeeService.saveEmployee(employee);
        LOGGER.info("CREATE " + employee);

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

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws CustomException {
        model.addAttribute("directors", directorService.getDirectorDepartmentMap());
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "employees/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult, Model model, @PathVariable("id") int id, @ModelAttribute("director") Director director) {
        if (bindingResult.hasFieldErrors("name")) {
            model.addAttribute("directors", directorService.getDirectorDepartmentMap());
            return "employees/edit";
        }
        employeeService.updateEmployee(id, employee);
        LOGGER.info("UPDATE " + employee);
        return "redirect:/employees";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
        LOGGER.info("DELETE employee witn id=" + id);
        return "redirect:/employees";
    }
}
