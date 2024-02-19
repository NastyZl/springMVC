package org.example.controllers;

import org.example.exception.CustomException;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionController {
    private static final Logger LOGGER = Logger.getLogger(DirectorsController.class);

    @ExceptionHandler(CustomException.class)
    public ModelAndView handlerException(CustomException e) {
        LOGGER.error(e.getMessage(), e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", e.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
