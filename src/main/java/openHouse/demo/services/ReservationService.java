
package openHouse.demo.services;

import java.util.Date;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.Reservation;
import openHouse.demo.exceptions.MiException;
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
    
<<<<<<< HEAD
=======
    public void modificarReserva(Date fechaInicio, Date fechaFin, 
     Integer cantPersonas, String idPropiedad, String idPropietario,String idReserva) throws MiException{
        
        
        validar(fechaInicio, fechaFin, cantPersonas);
        
        Optional<Reservation> respuestaReserva= reservationRepository.findById(idReserva);
        
        if (respuestaReserva.isPresent()) {
            Reservation reservation=respuestaReserva.get();
            reservation.setFechaInicio(fechaInicio);
            reservation.setFechaFin(fechaFin);
            reservation.setCantPersonas(cantPersonas);
            //DEFINIR COMO CALCULAMOS EL PRECIO FINAL !!LO MISMO PARA CALCULAS LOS DIAS QUE LAS NOCHES
            Double precioNuevo=precio(fechaFin, fechaFin, idPropietario);
            reservation.setPrecioFinal(precioNuevo);
            reservationRepository.save(reservation);
        }
    }
    
    public void eliminarReserva(String idReserva){
        Optional<Reservation> respuesta=reservationRepository.findById(idReserva);
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
    
    public void altaReserva(String id){
        Optional<Reservation> respuesta =reservationRepository.findById(id);
        
        if (respuesta.isPresent()) {
            Reservation reserva = respuesta.get();
            reserva.setAlta(Boolean.TRUE);
        }
    }
    
    
    ///revisar el calulo de los date!ESTO VA EN RESERVACION SERVICE
    public Double precio(Date alta,Date baja, String idPropiedad){
        
        Optional<Property> respuesta = propertyRepository.findById(idPropiedad);
        if (respuesta.isPresent()) {
            Property propiedad= respuesta.get();
            //faltaria poner precios individuales a la lista de prestaciones 
            Integer noches=calcularNoches(alta, baja);
            Double precio= propiedad.getPrecioBase() * noches;
            
           return precio;
        }
        return null;
    }
    
    
    public Integer calcularNoches(Date fechaInicio,Date fechaFin){
            long elapsedms=fechaFin.getTime()-fechaInicio.getTime();
            long diff = TimeUnit.MINUTES.convert(elapsedms, TimeUnit.MILLISECONDS);
            diff=(diff/1440);
            Integer noches;
            
            noches = Math.toIntExact(diff);
            return noches;
    }
    
>>>>>>> 0c0b9adeb220d1facf2de59d4518a5327ddf5f16
}
