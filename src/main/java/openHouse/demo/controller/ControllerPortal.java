package openHouse.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControllerPortal {
    
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }
}
