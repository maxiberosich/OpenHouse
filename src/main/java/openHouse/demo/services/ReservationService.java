
package openHouse.demo.services;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.Reservation;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.ClientRepository;
import openHouse.demo.repositories.PropertyRepository;
import openHouse.demo.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository reservationRepository;
        
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private PropertyRepository propertyRepository;
    
    @Autowired
    private ClientRepository clienteRepository;
    
    //como calculamos el precio final?
    //traer cliente con el id
    //traer propiedad con id
    @Transactional
    public void crearReservacion(Date fechaInicio, Date fechaFin, String idCliente,
     Integer cantPersonas, String idPropiedad ) throws MiException{
        
        
        Optional<Property> respuestaPropiedad=propertyRepository.findById(idPropiedad);
        Optional<Client> respuestaCliente=clienteRepository.findById(idCliente);
        
        validar(fechaInicio, fechaFin, cantPersonas);
                
        Reservation reservation = new Reservation();
        
        
        reservation.setFechaInicio(fechaInicio);
        reservation.setFechaFin(fechaFin);
        reservation.setCantPersonas(cantPersonas);
        reservation.setAlta(true);
        
        Client cliente =respuestaCliente.get();
        reservation.setCliente(cliente);
        
        
        Property propiedad=respuestaPropiedad.get();
        reservation.setPropiedad(propiedad);
        
        
        Double precioFinal=precio(fechaInicio, fechaFin, idPropiedad);
        reservation.setPrecioFinal(precioFinal);
        reservation.setCliente(clientService.getOne(cliente.getId()));
        
        List<Reservation> reservaciones=cliente.getReservaActiva();
        reservaciones.add(reservation);
        
        cliente.setReservaActiva(reservaciones);
        reservationRepository.save(reservation);
    }
     

    public void validar( Date fechaInicio, Date fechaFin,
            Integer cantPersonas ) throws MiException{
        if (fechaInicio == null) {
            throw new MiException("Seleccione una fecha de inicio de la reserva");
        }
        if (fechaFin == null) {
            throw new MiException("Seleccione una fecha de fin de la reserva");
        }  
        if (cantPersonas == null || cantPersonas == 0) {
            throw new MiException("La cantidad de personas no puede estar vacía o ser 0");
        }
    }
    
    public void modificarReserva(Date fechaInicio, Date fechaFin, 
     Integer cantPersonas, String idCliente, String idReserva) throws MiException{
        
        
        validar(fechaInicio, fechaFin, cantPersonas);
        
        Optional<Reservation> respuestaReserva= reservationRepository.findById(idReserva);
        
        if (respuestaReserva.isPresent()) {
            Reservation reservation=respuestaReserva.get();
            reservation.setFechaInicio(fechaInicio);
            reservation.setFechaFin(fechaFin);
            reservation.setCantPersonas(cantPersonas);
            //DEFINIR COMO CALCULAMOS EL PRECIO FINAL !!LO MISMO PARA CALCULAS LOS DIAS QUE LAS NOCHES
            Double precioNuevo=precio(fechaInicio, fechaFin, reservation.getPropiedad().getId());
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
            reservationRepository.save(reserva);
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
            
            //long diff = TimeUnit.MINUTES.convert(elapsedms, TimeUnit.MILLISECONDS);
            long diff=(elapsedms/86400000);
            Integer noches;
            noches = Math.toIntExact(diff);

            return noches;
    }
    

    public List<String> obtenerFechasGuardadas(String idPropiedad){
        List<Reservation> lista = reservationRepository.buscarPorPropiedad(idPropiedad);
        
        List<Date> fechasBloqueadas = new ArrayList();
        
        for (Reservation reservation : lista) {
            
            Calendar calendarInicio = Calendar.getInstance();
            calendarInicio.setTime(reservation.getFechaInicio());
            
            Calendar calendarFin = Calendar.getInstance();
            calendarFin.setTime(reservation.getFechaFin());
            
            long diasEntre = ChronoUnit.DAYS.between(calendarInicio.toInstant(), calendarFin.toInstant());
            
            for (int i = 0 ; i < diasEntre+1; i++) {
                
                Date fechaBloqueada = calendarInicio.getTime();
                fechasBloqueadas.add(fechaBloqueada);
                calendarInicio.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        List<String> fechasFinal = new ArrayList();
        
        for (Date fecha : fechasBloqueadas) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String fechaTexto = formatter.format(fecha);
            fechasFinal.add(fechaTexto);
        }
        return fechasFinal;
    }

    public Reservation getOne(String id){
       return reservationRepository.getOne(id);     
    }
    
    public void darDeBajaReserva(String idReserva){
        Optional<Reservation> respuesta = reservationRepository.findById(idReserva);
        
        if (respuesta.isPresent()) {
            Reservation reserva= respuesta.get();
            reserva.setAlta(false);
            reservationRepository.save(reserva);
        }
    }
    
    public List<Reservation> listaReservasActivas(String idPropietario){
        List<Reservation> listaReservas=reservationRepository.listaReservasActivas(idPropietario);
        List<Reservation> listaActiva=new ArrayList<>();
        
        
        for (Reservation reserva : listaReservas) {
            if (reserva.isAlta()) {
                listaActiva.add(reserva);
            }
        }
        return listaActiva;
    }
}
