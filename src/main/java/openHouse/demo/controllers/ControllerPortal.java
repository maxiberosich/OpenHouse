package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import openHouse.demo.entities.User;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.ClientService;
import openHouse.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/registrarCliente")
    public String registrarCliente() {
        return "registrar_usuario.html";
    }

    @PostMapping("/registroCliente")
    public String registroCliente(@RequestParam String name, @RequestParam String password, String password2,
            @RequestParam String email, @RequestParam String dni, @RequestParam String phone,
            @RequestParam Date birthdate, MultipartFile archivo, ModelMap model)  {
        try {

            clientService.createClient(name, password, password2, email, dni, phone, birthdate, archivo);
            model.put("exito", "Cliente registrado correctamente!");
            return "index.html";
        } catch (MiException ex) {

            model.put("error", ex.getMessage());
            return "registrar_usuario.html";
        }
    }
    
    @GetMapping("/registrarPropietario")
    public String registrarProp(){
        return "registro_propietario"; //completar con html para registrar propiedad
    }
    
    @PostMapping("/registroPropietario")
    public String registroPropietario(@RequestParam String name, @RequestParam String password, String password2,
            @RequestParam String email, @RequestParam String dni, @RequestParam String phone,
            @RequestParam Date birthdate,@RequestParam String cbu, MultipartFile archivo, ModelMap model){
        
        try {
            ownerService.crearPropietario(name,password,password2,email,dni,phone,birthdate,cbu,archivo);
            model.put("exito", "Propietario creado correctamente!");
            return "index.html";
        } catch (MiException ex) {
            model.put("error", ex.getMessage());
            return "inicio.html"; //completar con html para registrar propiedad
        }
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        
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
