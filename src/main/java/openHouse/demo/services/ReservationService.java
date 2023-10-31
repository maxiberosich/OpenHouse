
package openHouse.demo.services;

import java.util.Date;
import java.util.Optional;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.Reservation;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//FALTAN COSASA COMO :CALCULAR PRECIO FINAL -PRECIO BASE X NOCHE + PRESTACIONES(COMO INGRESO A LOS PRECIOS)+CANTIDAD DE PERSONAS
//CREO QUE PARA HACER MAS FACIL TODO TENEMOS QUE HACER METODOS : CALCULAR PRECIO /CALCULAR NOCHES/CALCULAR PRESTACIONES /CALCULAR PRESONAS.

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository reservationRepository;
    
    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private ClientService clientService;
    
    
    //como calculamos el precio final?
    //traer cliente con el id
    //traer propiedad con id
    @Transactional
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
        
        //en esta linea la idea es cargarle el precio a la reserva 
        propertyService.precio(fechaFin, fechaFin, propiedad.getId());
        
        //en esta linea la idea es cargarle el cliente a la reserva
        reservation.setCliente(clientService.getOne(cliente.getId()));
        
    }
     
    //que no sea nulo el id de cliente ni de propiedad
    public void validar( Date fechaInicio, Date fechaFin, Client cliente,
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
    
    public void modificarReserva(Date fechaInicio, Date fechaFin, Client cliente,
    Double precioFinal, Integer cantPersonas, Property propiedad, String idPropietario) throws MiException{
        
        
        validar(fechaInicio, fechaFin, cliente, precioFinal, cantPersonas, propiedad);
        
        Optional<Reservation> respuesta= reservationRepository.findById(idPropietario);
        
        if (respuesta.isPresent()) {
            Reservation reservation=respuesta.get();
            reservation.setFechaInicio(fechaInicio);
            reservation.setFechaFin(fechaFin);
            reservation.setCantPersonas(cantPersonas);
            //DEFINIR COMO CALCULAMOS EL PRECIO FINAL !!LO MISMO PARA CALCULAS LOS DIAS QUE LAS NOCHES
            reservationRepository.save(reservation);
        }
    }
    
    public void elimarReserva(String id){
        Optional<Reservation> respuesta=reservationRepository.findById(id);
        if (respuesta.isPresent()) {
            Reservation reserva=respuesta.get();
            reservationRepository.delete(reserva);
        }
    }
    
    public void bajaReserva(String id){
        Optional<Reservation> respuesta =reservationRepository.findById(id);
        
        if (respuesta.isPresent()) {
            Reservation reserva = respuesta.get();
            reserva.setAlta(Boolean.FALSE);
        }
    }
}
