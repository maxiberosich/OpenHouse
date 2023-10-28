
package openHouse.demo.services;

import java.util.Date;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.Reservation;
import openHouse.demo.exception.MiException;
import openHouse.demo.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository reservationRepository;
    
    @Transactional
    //como calculamos el precio final?
    //traer cliente con el id
    //traer propiedad con id
    public void crearReservacion(Date fechaInicio, Date fechaFin, Client cliente,
    Double precioFinal, Integer cantPersonas, Property propiedad ) throws MiException{
        //validar
                
        Reservation reservation = new Reservation();
        
        
        reservation.setFechaInicio(fechaInicio);
        reservation.setFechaFin(fechaFin);
        reservation.setCliente(cliente);
        reservation.setPrecioFinal(precioFinal);
        reservation.setCantPersonas(cantPersonas);
        reservation.setPropiedad(propiedad);
        
        
            
    }
     
    //que no sea nulo el id de cliente ni de propiedad
    public void Validar( Date fechaInicio, Date fechaFin, Client cliente,
    Double precioFinal, Integer cantPersonas, Property propiedad ) throws MiException{
        if (fechaInicio == null) {
            throw new MiException("Seleccione una fecha de inicio de la reserva");
        }
        if (fechaFin == null) {
            throw new MiException("Seleccione una fecha de fin de la reserva");
        }  
         if (fechaInicio == null) {
            throw new MiException("Seleccione una fecha de inicio");
        }
        if (cantPersonas == null || cantPersonas == 0) {
            throw new MiException("La cantidad de personas no puede estar vacía o ser 0");
        }
    }
    
}
