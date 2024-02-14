package org.example.controllers;


import org.example.NoVacancyForDirectorException;
import org.example.repository.DirectorRepository;
import org.example.model.Director;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/directors")
public class DirectorsController {
    private final DirectorRepository directorRepository;

    public DirectorsController(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("directors", directorRepository.index());
        return "directors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("director", directorRepository.show(id));
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
        if (directorRepository.existByDepartment(director.getDepartment())) {
            throw new NoVacancyForDirectorException("The last director has not been fired yet((((");
        }
        directorRepository.save(director);
        return "redirect:/directors";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("director", directorRepository.show(id));
        model.addAttribute("selectedDepartment", directorRepository.show(id).getDepartment());
        return "directors/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("director") @Valid Director director, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "directors/edit";
        directorRepository.update(id, director);
        return "redirect:/directors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        directorRepository.delete(id);
        return "redirect:/directors";
    }

    @ExceptionHandler(NoVacancyForDirectorException.class)
    public void handlerException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();

    }


}
