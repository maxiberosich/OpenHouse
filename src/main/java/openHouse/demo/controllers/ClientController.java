package openHouse.demo.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.ClientService;
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
@RequestMapping("/cliente")
public class ClientController {

    @Autowired
    private ClientService clienteService;

    @GetMapping("/registrarCliente")
    public String registrarCliente() {
        return "registrar.html";
    }

    @PostMapping("/registroCliente")
    public String registroCliente(@RequestParam String name, @RequestParam String password, String password2,
            @RequestParam String email, @RequestParam String dni, @RequestParam String phone,
            @RequestParam("birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
            @RequestParam(required = false) MultipartFile archivo, ModelMap model) throws IOException , MiException{

        try {
            clienteService.createClient(name, password, password2, email, dni, phone, birthdate, archivo);
            model.put("exito", "Cliente registrado correctamente!");
            return "redirect:/login";
        } catch (MiException | IOException ex) {

            model.put("error", ex.getMessage());
            model.put("name", name);
            model.put("email", email);
            model.put("dni", dni);
            model.put("phone", phone);
            return "registrar.html";
        }
    }

    @PreAuthorize("hasRole('ROLE_CLIENTE') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PROPIETARIO')")
    @PostMapping("/modificar/{id}")
    public String modificarCliente(@PathVariable String id, @RequestParam String name, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, @RequestParam String phone,
            @RequestParam String dni, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
            @RequestParam(required = false) MultipartFile archivo, ModelMap modelo) {

        try {

            clienteService.update(name, password, password2, email, dni, phone, birthdate, archivo, id);

            modelo.put("exito", "Cliente actualizado correctamente!");

            return "redirect:/";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("name", name);
            modelo.put("email", email);

            return "modificar_cliente";
        }
    }
}
