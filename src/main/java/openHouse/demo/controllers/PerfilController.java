package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.enums.Rol;
import openHouse.demo.services.OwnerService;
import openHouse.demo.services.PropertyService;
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

    @Autowired

    private OwnerService ownerService;
    
    @Autowired
    private PropertyService propertyService;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @GetMapping("/perfil")
    public String perfilUsuario(HttpSession session, ModelMap model) {
        User logeado = (User) session.getAttribute("usersession");
        if (logeado.getRol().toString().equals("CLIENTE")) {
            User cliente = userService.getOne(logeado.getId());
            model.put("usuario", cliente);
        } else if (logeado.getRol().toString().equals("PROPIETARIO")) {
            User propietario = ownerService.getOne(logeado.getId());
            List<Property> propiedades = propertyService.buscarPorPropiedad(propietario.getId());
            model.put("usuario", propietario);
            model.put("propertys", propiedades);
        }
        return "perfil.html";
    }

}
