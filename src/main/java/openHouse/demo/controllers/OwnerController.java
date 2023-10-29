package openHouse.demo.controllers;

import java.util.Date;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propietario")
public class OwnerController {
    
    @Autowired
    private OwnerService ownerService;
    
    @GetMapping("/registrarPropietario")
    public String registrarProp(){
        return "registrar_propietario.html"; //completar con html para registrar propiedad
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
            return "registrar_propietario.html"; //completar con html para registrar propiedad
        }
    }
    
}
