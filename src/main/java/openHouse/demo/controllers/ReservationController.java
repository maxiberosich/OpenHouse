package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import openHouse.demo.entities.User;
import openHouse.demo.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reserva")
public class ReservationController {
    @Autowired 
    private ReservationService reservaServicio;
    
    
    @GetMapping("/crear/{idPropiedad}")
    public String crearReserva(ModelMap modelo, HttpSession session,String idPropiedad){
        User user = (User) session.getAttribute("usersession");
        modelo.put("user", user);
        modelo.put("idPropiedad", idPropiedad);
        return "reservar_propiedad.html";
    }
    
    //@PostMapping
}
