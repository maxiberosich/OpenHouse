package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import openHouse.demo.entities.User;
import openHouse.demo.services.ClientService;
import openHouse.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ControllerPortal {

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/")
    public String inicio() {
        return "inicio.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos.");
        }
        
        return "login.html";
    }
        @GetMapping("/registrar")
    public String registrar(@RequestParam(required = false) String error, ModelMap modelo) {
        
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos.");
        }
        
        return "ingresar.html";
    }
    
    @PreAuthorize("hasRole('ROLE_CLIENTE') or hasRole('ROLE_PROPIETARIO') or hasRole('ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        
        User logeado = (User) session.getAttribute("usersession");
        
        if (logeado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        
        return "inicio.html";
    }
}
