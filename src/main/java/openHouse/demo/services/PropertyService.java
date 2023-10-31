package openHouse.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Owner;
import openHouse.demo.entities.Prestation;
import openHouse.demo.entities.Property;
import openHouse.demo.enums.City;
import openHouse.demo.enums.PropType;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.OwnerRepository;
import openHouse.demo.repositories.PrestationRepository;
import openHouse.demo.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PrestationRepository prestacionesRepository;
    /*
    @Autowired
    private ImageRepository imageRepository;
     */
    @Autowired
    private OwnerRepository owerRepository;

    @Transactional
    public void crearProperty(Double precioBase,
            String codigoPostal, String direccion, String descripcion, String idOwner,
            MultipartFile archivo, City ciudad, PropType tipoPropiedad) throws MiException {

        Optional<Owner> respuesta = owerRepository.findById(idOwner);

        if (respuesta.isPresent()) {
            validar(precioBase, codigoPostal, direccion, descripcion);

            Property propiedad = new Property();
            propiedad.setPrecioBase(precioBase);
            propiedad.setCodigoPostal(codigoPostal);
            propiedad.setDescripcion(descripcion);
            propiedad.setDireccion(direccion);
            propiedad.setAlta(Boolean.TRUE);
            propiedad.setCiudad(ciudad);
            propiedad.setTipo(tipoPropiedad);

            Owner propietario = respuesta.get();
            propiedad.setPropietario(propietario);

            Prestation prestaciones = new Prestation();
            prestaciones = selectPrestaciones(Integer.BYTES, Integer.MIN_VALUE, Integer.MIN_VALUE,
                    Integer.MIN_VALUE, true, true, true, true, true,
                    true, true, true, true, true, true, true);

            propiedad.setPrestaciones(prestaciones);

            propertyRepository.save(propiedad);
        }
    }

    @Transactional
    public List<Property> buscarPorCodigoPostal(String cp) {
        List<Property> propiedades = new ArrayList<>();
        propiedades = propertyRepository.buscarPorCodigoPostal(cp);
        return propiedades;
    }

    public void validar(Double precioBase,
            String codigoPostal, String direccion, String descripcion) throws MiException {

        if (precioBase == null) {
            throw new MiException("Por favor indicar el precio base por noche!Se podra modificar mas adelante !");
        }
        if (codigoPostal == null) {
            throw new MiException("Tiene que indicar el Codigo Postal de la propiedad , por favor.");
        }
        if (direccion == null) {
            throw new MiException("Tiene que indicar la direccion exacta de la propiedad , por favor.");
        }
        if (descripcion == null) {
            throw new MiException("Por favor debe insertar un comentario, ayudara mucho al cliente a tomar una decision.");
        }

    }

    public void bajaPropiedad(String id) {
        Optional<Property> respuesta = propertyRepository.findById(id);
        if (respuesta.isPresent()) {
            Property propiedad = respuesta.get();
            propiedad.setAlta(Boolean.FALSE);
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

    public List<Property> listaPropietarios() {
        List<Property> listaPropiedades = new ArrayList();
        listaPropiedades = propertyRepository.findAll();
        return listaPropiedades;
    }

    //crear metodo de rellenarprestaciones.
    public Prestation selectPrestaciones(Integer cantiPersonas, Integer cantAuto, Integer cantCuarto, Integer cantBanio, boolean pileta,
             boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi, boolean tv, boolean barra, boolean seAceptanMascotas,
            boolean aguaCorreinte, boolean cocina, boolean heladera, boolean microondas) {
        Prestation prestaciones = new Prestation();
        prestaciones.setCantidadPers(cantiPersonas);
        prestaciones.setCantAuto(cantAuto);
        prestaciones.setCantCuarto(cantCuarto);
        prestaciones.setCantBanio(cantBanio);
        prestaciones.setPileta(pileta);
        prestaciones.setAsador(asador);
        prestaciones.setCochera(cochera);
        prestaciones.setAireAcondicionado(aireAcondicionado);
        prestaciones.setWiFi(wiFi);
        prestaciones.setTv(tv);
        prestaciones.setBarra(barra);
        prestaciones.setSeAceptanMascotas(seAceptanMascotas);
        prestaciones.setAguaCorriente(aguaCorreinte);
        prestaciones.setCocina(cocina);
        prestaciones.setHeladera(heladera);
        prestaciones.setMicroondas(microondas);

        return prestaciones;

    }
    //crear metodo agregaro comentario, lotiene que agregar un cliente que haya tenido una reserva en la propiedad terminada y recien puede comentar .
    //crear metodo valoracion, lotiene que agregar un cliente que haya tenido una reserva en la propiedad terminada y recien puede comentar.
}
