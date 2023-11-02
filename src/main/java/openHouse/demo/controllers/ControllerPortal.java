package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.services.ClientService;
import openHouse.demo.services.OwnerService;
import openHouse.demo.services.PropertyService;
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
    
    @Autowired
    private PropertyService propertyService;

    @GetMapping("/")
    public String inicio(ModelMap modelo) {
        List<Property> propiedades = new ArrayList();
        propiedades = propertyService.listaPropietarios();
        modelo.put("propertys", propiedades);
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
    public String registrar() {
        return "registrar.html";
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
