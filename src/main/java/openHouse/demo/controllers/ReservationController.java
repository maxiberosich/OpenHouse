package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import openHouse.demo.entities.Client;
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
            return "confirm.html";
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
        
        modelo.addAttribute("reserva",clienteService.getOne(idCliente).getReservaActiva());
        modelo.addAttribute("cliente",clienteService.getOne(idCliente));
       
        return "lista_reserva.html";
    }
    
    @GetMapping("/modificarReserva/{idReserva}/{idCliente}")
    public String modificarReserva(@PathVariable String idReserva,@PathVariable String idCliente, ModelMap modelo){
        Reservation reserva= reservaServicio.getOne(idReserva);
        Client cliente = clienteService.getOne(idCliente);
        modelo.addAttribute("fechas", reservaServicio.obtenerFechasGuardadas(reserva.getPropiedad().getId()));
        modelo.put("reserva", reserva);
        modelo.put("cliente", cliente);
        return "modificar_reserva.html";
    }
    
    @PostMapping("/modificoReserva/{idReserva}/{idCliente}")
    public String modificoReserva(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date fechaFin, ModelMap modelo,
     @RequestParam Integer cantPersonas,@PathVariable String idReserva,@PathVariable String idCliente) throws MiException{
        try{
            /* modelo.addAttribute("reserva", reservaServicio.getOne(idReserva));
            modelo.addAttribute("property",propService.getOne(propertyId));*/
            reservaServicio.modificarReserva(fechaInicio, fechaFin, cantPersonas, idCliente,  idReserva);
            return "redirect:/reserva/list/{idCliente}";
        }catch(MiException e){
            modelo.put("error", e.getMessage());
            modelo.put("fechaInicio", fechaInicio);
            modelo.put("fechafin", fechaFin);
            modelo.put("cantPersonas", cantPersonas);
            return "modificar_reserva.html";
        }
        
    }
    
    @GetMapping("eliminarReserva/{idReserva}/{idCliente}")
    public String eliminarReserva(@PathVariable String idReserva, @PathVariable String idCliente){
        clienteService.eliminarReserva(idCliente, idReserva);
        reservaServicio.eliminarReserva(idReserva);
        
        return "cancel.html";
    }
    
    
}
