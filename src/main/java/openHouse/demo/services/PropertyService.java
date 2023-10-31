package openHouse.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Property;
import openHouse.demo.enums.City;
import openHouse.demo.enums.PropType;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertyService {
    
    @Autowired
    private PropertyRepository propertyRepository;
    
    /*
    @Autowired
    private PrestacionesRepository prestacionesRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private OwnerRepository owerRepository;
    */
    
    @Transactional
    public void crearProperty(String idPrestaciones, String idImagenes,String idPropietario,String idComentario,Double precioBase,
            String codigoPostal,String direccion,String descripcion , Double valoracion) throws MiException{
        
        validar(precioBase, codigoPostal, direccion, descripcion);
        //Owner propietario =owerRepositoryfindById(idPropietario).get();
        //Prestation prestaciones=prestacionesRepositoryfindById(idPrestaciones).get();
        
        Property propiedad=new Property();
        propiedad.setPrecioBase(precioBase);
        propiedad.setCodigoPostal(codigoPostal);
        propiedad.setDescripcion(descripcion);
        propiedad.setDireccion(direccion);
        propiedad.setAlta(Boolean.TRUE);
        propiedad.setCiudad(City.CORDOBA);
        propiedad.setTipo(PropType.CASA);
        //propiedad.setPrestaciones(prestaciones);
        //propiedad.setPropietario(propietario);
        //me falta cargar el roll city;
        //prototype
        
    }
         
    
    public void validar (Double precioBase,
            String codigoPostal,String direccion,String descripcion ) throws MiException{
       
        
        if (precioBase==null) {
            throw new MiException("Por favor indicar el precio base por noche!Se podra modificar mas adelante !");
        }
        if (codigoPostal==null) {
            throw new MiException("Tiene que indicar el Codigo Postal de la propiedad , por favor.");
        }
        if (direccion==null) {
            throw new MiException("Tiene que indicar la direccion exacta de la propiedad , por favor.");
        }
        if ( descripcion==null) {
            throw new MiException("Por favor debe insertar un comentario, ayudara mucho al cliente a tomar una decision.");
        }
       
    }
    
    public void bajaPropiedad(String id){
        Optional<Property> respuesta=propertyRepository.findById(id);
        if (respuesta.isPresent()) {
            Property propiedad=respuesta.get();
            propiedad.setAlta(Boolean.FALSE);
        }
    }
    
    public void eliminarPropiedad(String id){
        Optional<Property> respuesta=propertyRepository.findById(id);
        if (respuesta.isPresent()) {
            Property propiedad=respuesta.get();
            propertyRepository.delete(propiedad);
        }
    }
    
    public Property getOne(String id){
        return propertyRepository.getById(id);
    }
   
    public List<Property> listaPropietarios(){
        List<Property> listaPropiedades=new ArrayList();
        listaPropiedades= propertyRepository.findAll();
        return listaPropiedades;
    }
    ///revisar el calulo de los date!
    public Double precio(Date alta,Date baja, String id){
        Optional<Property> respuesta = propertyRepository.findById(id);
        if (respuesta.isPresent()) {
            Property propiedad=new Property();
            //faltaria poner precios individuales a la lista de prestaciones 
            Double precio= propiedad.getPrecioBase();
            //calcular los dias x precio !!!!
           return precio;
        }
        return null;
    }
}
