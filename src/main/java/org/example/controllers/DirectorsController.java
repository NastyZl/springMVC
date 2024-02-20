package org.example.controllers;


import org.example.exception.CustomException;
import org.example.models.Director;
import org.example.models.Employee;
import org.example.models.enums.Department;
import org.example.service.DirectorService;
import org.example.service.EmployeeService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Controller
@RequestMapping("/directors")
public class DirectorsController {
    private static final Logger LOGGER = Logger.getLogger(DirectorsController.class);
    private final EmployeeService employeeService;
    private final DirectorService directorService;

    public DirectorsController(EmployeeService employeeService, DirectorService directorService) {
        this.employeeService = employeeService;
        this.directorService = directorService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("directors", directorService.getAllDirectors());
        return "directors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) throws CustomException {
        model.addAttribute("director", directorService.getDirectorById(id));
        return "directors/show";
    }

    @GetMapping("/new")
    public String newDirector(Model model, @ModelAttribute("director") Director director) {
        model.addAttribute("departments", Department.values());
        return "directors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("director") @Valid Director director, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws CustomException {
        if (bindingResult.hasFieldErrors("name")) {
            return "directors/new";
        }
        if (directorService.isDirectorOfDepartmentPresent(director.getDepartment())) {
            throw new CustomException("The director of this department already exists. You need to fire him");
        }
        director.setId(directorService.getNewId());
        redirectAttributes.addFlashAttribute("director", director);
        return "redirect:/employees/add-to-director";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("director") @Valid Director director, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) throws CustomException {
        List<Employee> collect = employeeService.getAllEmployee().stream().filter(employee -> employee.getIdDirector() == director.getId()).collect(Collectors.toList());
        if (collect.size() < 3) {
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            redirectAttributes.addFlashAttribute("director", director);
            return "redirect:/employees/add-to-director";
        }
        Director directorSave = (Director) model.getAttribute("director");
        if (directorSave != null) {
            directorService.saveDirector(directorSave);
            LOGGER.info("CREATE " + directorSave);
            directorService.getDirectorById(directorSave.getId()).setSubordinateEmployees(employeeService.getAllEmployee().stream().filter(employee -> employee.getIdDirector() == directorSave.getId()).collect(Collectors.toList()));
        } else {
            throw new CustomException("chto-to tut ne tak...");
        }
        return "directors/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws CustomException {
        model.addAttribute("director", directorService.getDirectorById(id));
        return "directors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("director") @Valid Director director, BindingResult bindingResult, @PathVariable("id") int id) throws CustomException {
        if (bindingResult.hasErrors()) return "directors/edit";
        boolean errorDepartmentUnavailable = directorService.getAllDirectors().stream().anyMatch(d -> director.getDepartment() == d.getDepartment() && director.getId() != d.getId());
        if (errorDepartmentUnavailable) {
            throw new CustomException("The last director has not been fired yet((((");
        }
        directorService.updateDirector(id, director);
        LOGGER.info("UPDATE " + director);
        return "redirect:/directors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        directorService.deleteDirector(id);
        LOGGER.info("DELETE director witn id=" + id);
        return "redirect:/directors";
    }
}
