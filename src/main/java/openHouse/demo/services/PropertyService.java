package openHouse.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Image;
import openHouse.demo.entities.Owner;
import openHouse.demo.entities.Prestation;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.PropertyRepository;
import openHouse.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PrestationService prestationService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void crearProperty(Double precioBase,
            String codigoPostal, String direccion, String descripcion, String idOwner, MultipartFile archivo,
            String ciudad, String tipoPropiedad, Integer capMaxPersonas, Date fechaAlta, Date fechaBaja) throws MiException {

        validar(precioBase, codigoPostal, direccion, descripcion, capMaxPersonas);

        Optional<User> respuesta = userRepository.findById(idOwner);

        if (respuesta.isPresent()) {
            Property propiedad = new Property();
            propiedad.setPrecioBase(precioBase);
            propiedad.setCodigoPostal(codigoPostal);
            propiedad.setDescripcion(descripcion);
            propiedad.setDireccion(direccion);
            propiedad.setAlta(Boolean.TRUE);
            propiedad.setCiudad(ciudad);
            propiedad.setTipo(tipoPropiedad);
            propiedad.setPermitidoFiestas(Boolean.TRUE);
            propiedad.setCapMaxPersonas(capMaxPersonas);
            propiedad.setFechaAlta(fechaAlta);
            propiedad.setFechaBaja(fechaBaja);

            List<Image> listaImagen = new ArrayList();
            //Hago todo en uno, guardo la imagen y la cargo en la lista para despues enviarla con la imagen
            listaImagen.add(imageService.save(archivo));

            propiedad.setImagenes(listaImagen);

            User usuario = respuesta.get();
            Owner owner = (Owner) usuario;
            propiedad.setPropietario(owner);

            Prestation prestaciones = prestationService.createPrestation(2, 2, 3,
                    1, true, true, true, true, true,
                    true, true, true, true, true, true, true);

            propiedad.setPrestaciones(prestaciones);

            propertyRepository.save(propiedad);
        }
    }
    @Transactional
     public void modificarPropiedad(Double precioBase, String idProperty, Integer capMaxPersonas,
            String codigoPostal, String direccion, String descripcion, 
            MultipartFile archivo, String ciudad, String tipoPropiedad,Date fechaAlta,Date fechaBaja) throws MiException{
        
        validar(precioBase, codigoPostal, direccion, descripcion, capMaxPersonas);
        
        Optional<Property> respuesta= propertyRepository.findById(idProperty);
        
        if(respuesta.isPresent()){
            Property propiedad= respuesta.get();
            propiedad.setPrecioBase(precioBase);
            propiedad.setCapMaxPersonas(capMaxPersonas);
            propiedad.setCodigoPostal(codigoPostal);
            propiedad.setDireccion(direccion);
            propiedad.setDescripcion(descripcion);
            propiedad.setCiudad(ciudad);
            propiedad.setTipo(tipoPropiedad);
            propiedad.setFechaAlta(fechaAlta);
            propiedad.setFechaBaja(fechaBaja);
            
            List<Image> listaImagen = propiedad.getImagenes();
            //Hago todo en uno, guardo la imagen y la cargo en la lista para despues enviarla con la imagen
            listaImagen.add(imageService.save(archivo));

            propiedad.setImagenes(listaImagen);
            
            propertyRepository.save(propiedad);
                    
        }
        
    }

    @Transactional
    public List<Property> buscarPorCodigoPostal(String cp) {
        List<Property> propiedades = new ArrayList<>();
        propiedades = propertyRepository.buscarPorCodigoPostal(cp);
        return propiedades;
    }

    @Transactional
    public List<Property> buscarPorCiudad(String ciudad) {
        List<Property> propiedadesCiudad = new ArrayList();
        propiedadesCiudad = propertyRepository.buscarPorCiudad(ciudad);
        return propiedadesCiudad;
    }

    @Transactional
    public List<Property> buscarSegunPrecio(String precioMinimo, String precioMaximo) {
        List<Property> propiedadesSegunPrecio = new ArrayList();
        propiedadesSegunPrecio = propertyRepository.buscarSegunPrecio(precioMinimo, precioMaximo);
        return propiedadesSegunPrecio;
    }

    public void validar(Double precioBase, String codigoPostal, String direccion, String descripcion, Integer capMaxPersonas) throws MiException {
        if (precioBase == null) {
            throw new MiException("Por favor indicar el precio base por noche! Se podrá modificar más adelante!");
        }
        if (codigoPostal == null) {
            throw new MiException("Tiene que indicar el Código Postal de la propiedad, por favor.");
        }
        if (direccion == null) {
            throw new MiException("Tiene que indicar la dirección exacta de la propiedad, por favor.");
        }
        if (descripcion == null) {
            throw new MiException("Por favor debe insertar un comentario, ayudará mucho al cliente a tomar una decisión.");
        }
        if (capMaxPersonas == null) {
            throw new MiException("Por favor, debe especificar la capacidad máxima de personas para la propiedad");
        }
    }

    public void bajaPropiedad(String id) {
        Optional<Property> respuesta = propertyRepository.findById(id);
        if (respuesta.isPresent()) {
            Property propiedad = respuesta.get();
            propiedad.setAlta(Boolean.FALSE);
        }
    }

    public void permisionFiesta(String id) {
        Optional<Property> respuesta = propertyRepository.findById(id);
        if (respuesta.isPresent()) {
            Property propiedad = respuesta.get();
            propiedad.setPermitidoFiestas(Boolean.FALSE);
        }
    }

    public void eliminarPropiedad(String id) {
        Optional<Property> respuesta = propertyRepository.findById(id);
        if (respuesta.isPresent()) {
            Property propiedad = respuesta.get();
            propertyRepository.delete(propiedad);
        }
    }

    public Property getOne(String id) {
        return propertyRepository.getById(id);
    }

    public List<Property> listaPropiedades() {
        List<Property> listaPropiedades = propertyRepository.findAll();
        return listaPropiedades;
    }

    @Transactional
    public Property buscarPropiedad(String id) {
        Optional<Property> propiedadSeleccionada = propertyRepository.findById(id);
        Property propiedad = propiedadSeleccionada.get();
        return propiedad;
    }

    //crear metodo agregar comentario, lo tiene que agregar un cliente que haya tenido una reserva en la propiedad terminada y recien puede comentar .
    //crear metodo valoracion, lotiene que agregar un cliente que haya tenido una reserva en la propiedad terminada y recien puede comentar.
}
