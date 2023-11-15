package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.Reservation;
import openHouse.demo.entities.User;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.ClientService;
import openHouse.demo.services.PropertyService;
import openHouse.demo.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reserva")
public class ReservationController {
    @Autowired 
    private ReservationService reservaServicio;
    
    @Autowired
    private PropertyService propService;
    
    @Autowired
    private ClientService clienteService;
    //
    @GetMapping("/crear/{idPropiedad}")
    public String crearReserva(ModelMap modelo, HttpSession session,@PathVariable String idPropiedad){
        User user = (User) session.getAttribute("usersession");
        modelo.put("user", user);
        modelo.addAttribute("fechas", reservaServicio.obtenerFechasGuardadas(idPropiedad));
        //modelo.put("idPropiedad", idPropiedad);
       
        
        Property propiedad = propService.getOne(idPropiedad);
        modelo.addAttribute("propertys", propiedad);
        
        return "reservar_propiedad.html";
    }
    
    @PostMapping("/crearReserva/{idPropiedad}/{idCliente}")
    public String creacionReserva(ModelMap modelo,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaInicio,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaFin,@PathVariable String idCliente, @RequestParam String cantPersonas,
            @PathVariable String idPropiedad){
        
        try {
            reservaServicio.crearReservacion(fechaInicio, fechaFin, idCliente,Integer.valueOf(cantPersonas) , idPropiedad);
            
            modelo.put("exito", "Reservacion agendada correctamente");
             System.out.println(cantPersonas);
            return "redirect:/";
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("fechaInicio", fechaInicio);
            modelo.put("fechaFin", fechaFin);
            modelo.put("cantPersonas", cantPersonas);
            return "reservar_propiedad.html";
        }
    }
    
    @GetMapping("/list/{idCliente}")
    public String mostrarReserva(ModelMap modelo,@PathVariable String idCliente){
        //ESTO ANDA 
        modelo.addAttribute("reserva",clienteService.getOne(idCliente).getReservaActiva());
       
        return "lista_reserva.html";
    }
}
