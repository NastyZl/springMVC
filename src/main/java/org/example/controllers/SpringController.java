package org.example.controllers;

import org.example.repository.DirectorRepository;
import org.example.model.enums.Department;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SpringController {
    private final DirectorRepository directorRepository;

    public SpringController(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @GetMapping("/{id}")
    public String showDepartment(Model model, @PathVariable int id) {
        model.addAttribute("director", directorRepository.showDirectorOfDepartment(id));
        return "show";
    }
    @GetMapping()
    public String  index(Model model) {
        model.addAttribute("departments", Department.values());
        return "index";
    }


}
