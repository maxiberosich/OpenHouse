package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.services.PropertyService;
import openHouse.demo.services.UserService;
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
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String inicio(ModelMap modelo) {
        List<Property> propiedades = propertyService.listaPropiedades();
        modelo.addAttribute("propertys", propiedades);
        for (Property propiedad : propiedades) {
            propertyService.valoracionPropiedad(propiedad.getId());
        }
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
    public String inicio(HttpSession session) {

        User logeado = (User) session.getAttribute("usersession");

        if (logeado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/";
        }
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PROPIETARIO')")
    @GetMapping("/modificarPerfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        User usuario = (User) session.getAttribute("usersession");

        if (usuario.getRol().toString().equals("PROPIETARIO")) {
            User usuarioP = userService.getOne(usuario.getId());

            modelo.put("cliente", usuarioP);
            return "modificarpropietario.html";
        }

        if (usuario.getRol().toString().equals("CLIENTE")) {
            User usuarioC = userService.getOne(usuario.getId());

            modelo.put("cliente", usuarioC);
            return "modificar_cliente.html";
        } else {
            return null;
        }

    }

}
