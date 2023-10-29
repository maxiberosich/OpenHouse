package openHouse.demo.controllers;

import java.util.Date;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "registrar_cliente.html";
    }

    @PostMapping("/registroCliente")
    public String registroCliente(@RequestParam String name, @RequestParam String password, String password2,
            @RequestParam String email, @RequestParam String dni, @RequestParam String phone,
            @RequestParam("birthdate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate, MultipartFile archivo, ModelMap model)  {
        try {

            clienteService.createClient(name, password, password2, email, dni, phone, birthdate, archivo);
            model.put("exito", "Cliente registrado correctamente!");
            return "inicio.html";
        } catch (MiException ex) {

            model.put("error", ex.getMessage());
            return "registrar_cliente.html";
        }
    }
}
