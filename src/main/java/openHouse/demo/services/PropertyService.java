package openHouse.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Comment;
import openHouse.demo.entities.Image;
import openHouse.demo.entities.Owner;
import openHouse.demo.entities.Prestation;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.ClientRepository;
import openHouse.demo.repositories.CommentRepository;
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

    @Autowired
    private ClientRepository clienteRepository;
    
    @Autowired
    private CommentRepository comentarioRepositorio;

    @Transactional
    public void crearProperty(Double precioBase,
            String codigoPostal, String direccion, String descripcion, String idOwner, MultipartFile[] archivo,
            String ciudad, String tipoPropiedad, Integer capMaxPersonas, Date fechaAlta, Date fechaBaja,
            //de aca para abajo son atributos de prestaciones.
            Integer cantidadPers, Integer cantAuto, Integer cantCuarto, Integer cantBanio,
            boolean pileta, boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi,
            boolean tv, boolean barra, boolean seAceptanMascotas, boolean aguaCorriente, boolean cocina,
            boolean heladera, boolean microondas) throws MiException {

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
            if(archivo != null && archivo.length>0){
                for (MultipartFile file : archivo) {
                    listaImagen.add(imageService.save(file));

                }
            }
            
            propiedad.setImagenes(listaImagen);

            User usuario = respuesta.get();
            Owner owner = (Owner) usuario;
            propiedad.setPropietario(owner);            
            
            Prestation prestacion=prestationService.createPrestation
                    ( cantidadPers, cantAuto, cantCuarto, cantBanio,
                    pileta, asador, cochera, aireAcondicionado, wiFi, tv, barra, seAceptanMascotas,
                    aguaCorriente, cocina, heladera, microondas);
            
            propiedad.setPrestaciones(prestacion);
            
            propertyRepository.save(propiedad);
        }
    }

    @Transactional
    public void modificarPropiedad(Double precioBase, String idProperty, Integer capMaxPersonas,
            String codigoPostal, String direccion, String descripcion,
            MultipartFile archivo, String ciudad, String tipoPropiedad, Date fechaAlta, Date fechaBaja,
            
            Integer cantidadPers, Integer cantAuto, Integer cantCuarto, Integer cantBanio,
            boolean pileta, boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi,
            boolean tv, boolean barra, boolean seAceptanMascotas, boolean aguaCorriente, boolean cocina,
            boolean heladera, boolean microondas) throws MiException {

        validar(precioBase, codigoPostal, direccion, descripcion, capMaxPersonas);

        Optional<Property> respuesta = propertyRepository.findById(idProperty);

        if (respuesta.isPresent()) {
            Property propiedad = respuesta.get();
            propiedad.setPrecioBase(precioBase);
            propiedad.setCapMaxPersonas(capMaxPersonas);
            propiedad.setCodigoPostal(codigoPostal);
            propiedad.setDireccion(direccion);
            propiedad.setDescripcion(descripcion);
            propiedad.setCiudad(ciudad);
            propiedad.setTipo(tipoPropiedad);
            propiedad.setFechaAlta(fechaAlta);
            propiedad.setFechaBaja(fechaBaja);
            
            if(!archivo.isEmpty()){
                List<Image> listaImagen = propiedad.getImagenes();
                listaImagen.add(imageService.save(archivo));
                propiedad.setImagenes(listaImagen);
            }
            
            Prestation prestacion = prestationService.update(cantidadPers, cantAuto, cantCuarto, cantBanio, pileta,
                    asador, cochera, aireAcondicionado, wiFi, tv, barra, seAceptanMascotas, aguaCorriente, cocina,
                    heladera, microondas, propiedad.getPrestaciones().getId());
            
            propiedad.setPrestaciones(prestacion);
            
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
    public List<Property> buscarPorPropietario(String idPropietario){
        List<Property> propiedades = new ArrayList();
        propiedades = propertyRepository.buscarPorPropietario(idPropietario);
        return propiedades;
    }

    @Transactional
    public List<Property> buscarSegunPrecio(String precioMinimo, String precioMaximo) {
        List<Property> propiedadesSegunPrecio = new ArrayList();
        propiedadesSegunPrecio = propertyRepository.buscarSegunPrecio(precioMinimo, precioMaximo);
        return propiedadesSegunPrecio;
    }
    
    @Transactional
    public List<Property> buscarPorPropiedad(String id) {
        List<Property> propiedadesId = new ArrayList();
        propiedadesId = propertyRepository.buscarPorPropietario(id);
        return propiedadesId;
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
        return propertyRepository.getOne(id);
    }

    public List<Property> listaPropiedades() {
        List<Property> listaPropiedades = propertyRepository.findAll();
        
        return listaPropiedades;
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


    

    //crear metodo agregar comentario, lo tiene que agregar un cliente que haya tenido una reserva en la propiedad terminada y recien puede comentar .
    @Transactional
    public void agregarComentario(String idCliente, String idPopiedad, String cuerpo, Double valoracion,
             MultipartFile archivo) throws MiException {

        Optional<Client> respuestaCliente = clienteRepository.findById(idCliente);
        Optional<Property> respuestaPropiedad = propertyRepository.findById(idPopiedad);

        if (respuestaCliente.isPresent() && respuestaPropiedad.isPresent()) {
            Property propiedad = respuestaPropiedad.get();
            Comment nuevoComentario = new Comment();
            Client cliente = respuestaCliente.get();
            Image imagenCliente = imageService.save(archivo);

            nuevoComentario.setCliente(cliente);
            nuevoComentario.setCuerpo(cuerpo);
            nuevoComentario.setValoracion(valoracion);
            nuevoComentario.setImagen(imagenCliente);

            List<Comment> listaComentarios = propiedad.getComentarios();
            listaComentarios.add(nuevoComentario);
            propiedad.setComentarios(listaComentarios);

            propertyRepository.save(propiedad);
        }
    }
    
    
    

    public void valoracionPropiedad(String idPropiedad) {
        Optional<Property> respuesta = propertyRepository.findById(idPropiedad);

        Integer cantidadComentarios = 0;
        Double valorFinal = 0.0;

        if (respuesta.isPresent()) {

            Property propiedad = respuesta.get();
            List<Comment> comentarios2 = comentarioRepositorio.buscarPorIdPropiedad(idPropiedad);
            if (comentarios2.size()!=0) {
                List<Comment> comentarios = comentarioRepositorio.buscarPorIdPropiedad(idPropiedad);
                for (Comment object : comentarios) {
                    valorFinal = valorFinal + object.getValoracion();
                }
                propiedad.setValoracion(Math.ceil(valorFinal / comentarios.size()));
                propertyRepository.save(propiedad);
            }
        }
    }
    
}
