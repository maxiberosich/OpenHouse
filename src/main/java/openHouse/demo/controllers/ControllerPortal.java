package openHouse.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControllerPortal {
    
    @GetMapping("/")
    public String inicio(){
        return "index.html";
    }
}
