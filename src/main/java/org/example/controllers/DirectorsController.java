package org.example.controllers;


import org.example.NoVacancyForDirectorException;

import org.example.models.Director;
import org.example.service.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
@RequestMapping("/directors")
public class DirectorsController {
    private final DirectorService directorService;

    public DirectorsController(DirectorService directorService) {
        this.directorService = directorService;
    }

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
    public String newDirector(@ModelAttribute("director") Director director) {
        return "directors/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("director") @Valid Director director, BindingResult bindingResult) throws NoVacancyForDirectorException {
        if (bindingResult.hasErrors())
            return "directors/new";
        if (directorService.isDirectorOfDepartmentPresent(director.getDepartment())) {
            throw new NoVacancyForDirectorException("The last director has not been fired yet((((");
        }
        directorService.saveDirector(director);
        System.out.println(director);
        System.out.println(directorService.getDirectorById(1));
        return "redirect:/directors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("director", directorService.getDirectorById(id));
        model.addAttribute("selectedDepartment", directorService.getDirectorById(id).getDepartment());
        return "directors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("director") @Valid Director director, BindingResult bindingResult, @PathVariable("id") int id) {
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
