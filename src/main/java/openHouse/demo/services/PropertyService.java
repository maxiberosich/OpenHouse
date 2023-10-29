package openHouse.demo.services;

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
    
   
}
