package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import openHouse.demo.entities.User;
import openHouse.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class PerfilController {
    
    @Autowired
    private UserService userService;


    
    
    
        

        
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfilUsuario(HttpSession session, ModelMap model) { 
        User logeado = (User) session.getAttribute("usersession");
        User cliente = userService.getOne(logeado.getId());
        model.put("usuario", cliente);

        return "perfil.html";
    }

}