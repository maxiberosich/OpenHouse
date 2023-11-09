package openHouse.demo.controllers;

import openHouse.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class PerfilController {
    
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil/{id}")
    public String perfil(ModelMap modelo, HttpSession session) {
        User user = (User) session.getAttribute("usersession");
        if (user != null) {
            modelo.put("user", user);
            return "perfil.html";
        } else {
            return "inicio.html";
        }
    }

        
        return "perfil.html";
    }
    
    
    
}