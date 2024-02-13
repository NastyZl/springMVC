package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SpringController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
    @GetMapping("/")
    public @ResponseBody ResponseEntity<String> sayHello1() {
        return new ResponseEntity<>("GET Response: ", HttpStatus.NOT_FOUND);
    }


}
