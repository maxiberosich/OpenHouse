package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import openHouse.demo.entities.User;
import openHouse.demo.repositories.UserRepository;
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
    private UserRepository userRepository;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil${id}")
    public String perfil(ModelMap modelo, HttpSession session) {
        User user = (User) session.getAttribute("usersession");
        if (user != null) {
            modelo.put("user", user);
            return "perfil.html";
        } else {
            return "inicio.html";
        }
    }

}
