package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import openHouse.demo.entities.Owner;
import openHouse.demo.entities.User;
import openHouse.demo.enums.City;
import openHouse.demo.enums.PropType;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.OwnerService;
import openHouse.demo.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propietario")
public class OwnerController {
    
    @Autowired
    private OwnerService ownerService;
    
    @Autowired
    private PropertyService propertyService;
    
    @GetMapping("/registrarPropietario")
    public String registrarProp(){
        return "ingresar.html"; //completar con html para registrar propiedad
    }
    
    @PostMapping("/registroPropietario")
    public String registroPropietario(@RequestParam String name, @RequestParam String password, String password2,
            @RequestParam String email, @RequestParam String dni, @RequestParam String phone,
            @RequestParam("birthdate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,@RequestParam String cbu, MultipartFile archivo, ModelMap model){
        
        try {
            ownerService.crearPropietario(name,password,password2,email,dni,phone,birthdate,cbu,archivo);
            model.put("exito", "Propietario creado correctamente!");
            return "inicio.html";
        } catch (MiException ex) {
            model.put("error", ex.getMessage());
            model.put("name",name);
            model.put("email",email);
            model.put("dni",dni);
            model.put("phone",phone);
            model.put("cbu",cbu);
            return "inicio.html"; //completar con html para registrar propiedad
        }
    }
    
    @GetMapping("/registrarPropiedad")
    public String registrarPropiedad(ModelMap modelo, HttpSession session){
        User user = (User) session.getAttribute("usersession");
        modelo.put("user", user);
        return "registrar_propiedad.html";
    }
    
    @PostMapping("/registrarPropiedad/{id}")
    public String  registroPropiedad(@PathVariable String id, @RequestParam Double precioBase, @RequestParam String codigoPostal, 
            @RequestParam String direccion, @RequestParam String descripcion, ModelMap modelo,MultipartFile archivo,City ciudad, PropType tipoPropiedad){
        
        try {
            propertyService.crearProperty(precioBase, codigoPostal, direccion, descripcion, id, archivo, ciudad, tipoPropiedad);
            modelo.put("exito", "Propiedad cargada correctamente");
            return "registrar_propiedad.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("precioBase",precioBase);
            modelo.put("codigoPostal",codigoPostal);
            modelo.put("direccion",direccion);
            modelo.put("descripcion",descripcion);
            return "registrar_propiedad.html";
        }
    }
    
}
