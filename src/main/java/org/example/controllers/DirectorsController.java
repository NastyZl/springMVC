package org.example.controllers;


import org.example.NoVacancyForDirectorException;
import org.example.models.Director;
import org.example.models.enums.Department;
import org.example.service.DirectorService;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/directors")
public class DirectorsController {
    private final EmployeeService employeeService;

    public DirectorsController(EmployeeService employeeService, DirectorService directorService) {
        this.employeeService = employeeService;
        this.directorService = directorService;
    }

    private final DirectorService directorService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("directors", directorService.getAllDirectors());
        return "directors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("director", directorService.getDirectorById(id));
        return "directors/show";
    }

    @GetMapping("/new")
    public String newDirector(Model model, @ModelAttribute("director") Director director) {
        model.addAttribute("departments", Department.values());
        return "directors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("director") @Valid Director director, BindingResult
            bindingResult, RedirectAttributes redirectAttributes, Model model) throws NoVacancyForDirectorException {
        if (bindingResult.hasFieldErrors("name")) {
            return "directors/new";
        }
        if (directorService.isDirectorOfDepartmentPresent(director.getDepartment())) {
            throw new NoVacancyForDirectorException("The last director has not been fired yet((((");
        }
        director.setId(directorService.getNewId());
        redirectAttributes.addFlashAttribute("director", director);
        return "redirect:/employees/new";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("director") @Valid Director director, BindingResult
            bindingResult, RedirectAttributes redirectAttributes, Model model) throws NoVacancyForDirectorException {
        if (bindingResult.hasFieldErrors("subordinateEmployees")) {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/employees/new", String.class);
//            String body = entity.getBody();
//            System.out.println(body);
            String error = "error";
            model.addAttribute("error", error);
//            String result = restTemplate.getForObject("http://localhost:8080/employees/new", String.class);
//            System.out.println(result);
//            return result;
//            ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/employees/new", HttpMethod.GET, null, String.class);

//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                String result = responseEntity.getBody();
//                return result;
//            } else {
//                // обработка ошибки
//                return "error";
//            }
        }
        Director directorSave = (Director) model.getAttribute("director");
        if (directorSave != null) {
            directorService.saveDirector((Director) model.getAttribute("director"));
            directorService.getDirectorById(directorSave.getId()).setSubordinateEmployees(employeeService.getAllEmployee().stream().filter(employee -> employee.getIdDirector() == directorSave.getId()).collect(Collectors.toList()));
        } else {
            throw new RuntimeException("вы пытаетесь сохранить директора без самого директора...  так нельзя");
        }
        return "directors/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("director", directorService.getDirectorById(id));
        model.addAttribute("selectedDepartment", directorService.getDirectorById(id).getDepartment());
        return "directors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("director") @Valid Director director, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "directors/edit";
        directorService.updateDirector(id, director);
        return "redirect:/directors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        directorService.deleteDirector(id);
        return "redirect:/directors";
    }

//    @ExceptionHandler(NoVacancyForDirectorException.class)
//    public void handlerException(Exception exception) {
//        ModelAndView modelAndView = new ModelAndView();
//
//    }


}
