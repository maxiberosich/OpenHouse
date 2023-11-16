package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import openHouse.demo.entities.Owner;
import openHouse.demo.entities.User;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.OwnerService;
import openHouse.demo.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ReservationService reservaServicio;
    
    
    @GetMapping("/registrarPropietario")
    public String registrarProp(){
        return "registrar.html"; 
    }
    
    @PostMapping("/registroPropietario")
    public String registroPropietario(@RequestParam String name, @RequestParam String password, String password2,
            @RequestParam String email, @RequestParam String dni, @RequestParam String phone,
            @RequestParam("birthdate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
            @RequestParam String cbu,@RequestParam(required = false) MultipartFile archivo, ModelMap model) throws IOException,MiException{

        
        try {
            ownerService.crearPropietario(name,password,password2,email,dni,phone,birthdate,cbu,archivo);
            model.put("exito", "Propietario creado correctamente!");
            return "redirect:/login";
        } catch (MiException ex) {
            model.put("error", ex.getMessage());
            model.put("name",name);
            model.put("email",email);
            model.put("dni",dni);
            model.put("phone",phone);
            model.put("cbu",cbu);
            return "registrar.html"; 
        } catch (IOException ex) {
            model.put("error", ex.getMessage());
            model.put("name",name);
            model.put("email",email);
            model.put("dni",dni);
            model.put("phone",phone);
            model.put("cbu",cbu);
        return "registrar.html";
        }
    }
    
    @PreAuthorize("hasRole('ROLE_CLIENTE') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PROPIETARIO')")
    @PostMapping("/modificar/{id}")
    public String modificarPropietario(@PathVariable String id, @RequestParam String name, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, @RequestParam String phone,
            @RequestParam String dni, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
            @RequestParam String cbu,@RequestParam(required = false) MultipartFile archivo, ModelMap modelo)throws MiException {
        
        try {

            ownerService.modificarPropietario(name, password, password2, email, dni, phone, birthdate, cbu,archivo, id);

            modelo.put("exito", "Cliente actualizado correctamente!");

            return "redirect:/";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("name", name);
            modelo.put("email", email);

            return "modificar_cliente.html";
        }
    }
    
    @GetMapping("/listDeReservas")
    public String mostrarReserva(ModelMap modelo, HttpSession session){
        User usuario = (Owner) session.getAttribute("usersession");
        
        modelo.addAttribute("reservaLista",reservaServicio.listaReservasActivas(usuario.getId()));
        modelo.addAttribute("propietario",ownerService.getOne(usuario.getId()));
        
        return "lista_reservaPropietario.html";
    }
    
    @GetMapping("/listDeReservas/{idReserva}")
    public String cancelar(@PathVariable String idReserva,ModelMap modelo) {
        
        try {
            reservaServicio.bajaReserva(idReserva);
            modelo.put("exito", "Reserva cancelada.");
            return "redirect:/propietario/listDeReservas";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            
            return "redirect:/propietario/listDeReservas";
        }
    }
}
