package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
<<<<<<< Updated upstream
import openHouse.demo.enums.Rol;
=======
import openHouse.demo.services.OwnerService;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    private PropertyService propertyService;

    @PreAuthorize("hasRole('ROLE_CLIENTE') or hasRole('ROLE_PROPIETARIO') or hasRole('ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfilUsuario(HttpSession session, ModelMap model) {
        User logeado = (User) session.getAttribute("usersession");
        User cliente = userService.getOne(logeado.getId());
        model.put("usuario", cliente);
        if (cliente.getRol().equals(Rol.PROPIETARIO)) {
            
            model.addAttribute("propertys", propertyService
                    .buscarPorPropietario(cliente.getId()));
        }

=======
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
            model.put("propiedades", propiedades);
        }
>>>>>>> Stashed changes
        return "perfil.html";
    }

}
