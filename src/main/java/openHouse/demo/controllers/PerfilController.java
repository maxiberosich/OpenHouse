package openHouse.demo.controllers;

import openHouse.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class PerfilController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/perfil")
    public String perfil() {        
        return "perfil.html";
    }

}