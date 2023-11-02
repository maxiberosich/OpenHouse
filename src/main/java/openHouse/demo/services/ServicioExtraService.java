package openHouse.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.ServicioExtra;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.ServicioExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioExtraService {
      
    @Autowired
    private ServicioExtraRepository servicioExtraRepo;
    
    @Transactional
    public void createServicioExtra(String servicio, String descripcion, Double precio) throws MiException{
        
        validate(servicio, precio);
        
        ServicioExtra servicioExtra = new ServicioExtra();
        
        servicioExtra.setServicio(servicio);
        servicioExtra.setDescripcion(descripcion);
        servicioExtra.setPrecio(precio);
        
        servicioExtraRepo.save(servicioExtra);
    }
    
    public void validate(String servicio, Double precio) throws MiException{
        
        if (servicio.isEmpty() || servicio == null) {
            throw new MiException("El nombre del servicio no puede estar vacio o ser nulo.");
        }
        if (precio.isNaN()) {
            throw new MiException("Debe definir un precio para el servicio.");
        }
    }
    
    public List<ServicioExtra> listarServiciosExtra(){
        List<ServicioExtra> serviciosExtra = new ArrayList();
        
        serviciosExtra = servicioExtraRepo.findAll();
        
        return serviciosExtra;
    }
    
    public void updateServicioExtra(String id, String servicio, String descripcion, Double precio) throws MiException{
        
        validate(servicio, precio);
        
        Optional<ServicioExtra> respuesta = servicioExtraRepo.findById(id);
        
        if (respuesta.isPresent()) {
            
            ServicioExtra servicioExtra = new ServicioExtra();
            
            servicioExtra.setServicio(servicio);
            servicioExtra.setDescripcion(descripcion);
            servicioExtra.setPrecio(precio);
            
            servicioExtraRepo.save(servicioExtra);
        }
    }
    
    public ServicioExtra getOne(String id){
        return servicioExtraRepo.getOne(id);
    }
    
    public void deleteServicioExtra(String id){
        
        Optional<ServicioExtra> respuesta = servicioExtraRepo.findById(id);
        
        if (respuesta.isPresent()) {
            
            ServicioExtra servicioExtra = respuesta.get();
            
            servicioExtraRepo.delete(servicioExtra);
        }
    }
}
