package openHouse.demo.services;

import java.util.Optional;
import openHouse.demo.entities.Prestation;
import openHouse.demo.entities.Property;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.PrestationRepository;
import openHouse.demo.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestationService {

    @Autowired
    private PrestationRepository prestationRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public Prestation createPrestation(Integer cantidadPers, Integer cantAuto, Integer cantCuarto, Integer cantBanio,
            boolean pileta, boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi,
            boolean tv, boolean barra, boolean seAceptanMascotas, boolean aguaCorriente, boolean cocina,
            boolean heladera, boolean microondas) throws MiException {

        validate(cantidadPers, cantAuto, cantCuarto, cantBanio);

        //Optional<Property> respuestaProperty = propertyRepository.findById(idPropiedad);

        //if (respuestaProperty.isPresent()) {
            //Property propiedad=respuestaProperty.get();
            
            
            Prestation prestacion = new Prestation();

            prestacion.setAguaCorriente(aguaCorriente);
            prestacion.setAireAcondicionado(aireAcondicionado);
            prestacion.setAsador(asador);
            prestacion.setBarra(barra);
            prestacion.setCantAuto(cantAuto);
            prestacion.setCantBanio(cantBanio);
            prestacion.setCantCuarto(cantCuarto);
            prestacion.setCantidadPers(cantidadPers);
            prestacion.setCochera(cochera);
            prestacion.setCocina(cocina);
            prestacion.setHeladera(heladera);
            prestacion.setMicroondas(microondas);
            prestacion.setPileta(pileta);
            prestacion.setSeAceptanMascotas(seAceptanMascotas);
            prestacion.setTv(tv);
            prestacion.setWiFi(wiFi);
            //prestacion.setPropiedad(propiedad);
            prestationRepository.save(prestacion);
            return prestacion;
        //}
            //return null;
    }

    @Transactional
    public void update(Integer cantidadPers, Integer cantAuto, Integer cantCuarto, Integer cantBanio,
            boolean pileta, boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi,
            boolean tv, boolean barra, boolean seAceptanMascotas, boolean aguaCorriente, boolean cocina,
            boolean heladera, boolean microondas, String idPrestation) throws MiException {

        validate(cantidadPers, cantAuto, cantCuarto, cantBanio);

        Optional<Prestation> respuesta = prestationRepository.findById(idPrestation);

        if (respuesta.isPresent()) {

            Prestation prestacion = respuesta.get();

            prestacion.setAguaCorriente(aguaCorriente);
            prestacion.setAireAcondicionado(aireAcondicionado);
            prestacion.setAsador(asador);
            prestacion.setBarra(barra);
            prestacion.setCantAuto(cantAuto);
            prestacion.setCantBanio(cantBanio);
            prestacion.setCantCuarto(cantCuarto);
            prestacion.setCantidadPers(cantidadPers);
            prestacion.setCochera(cochera);
            prestacion.setCocina(cocina);
            prestacion.setHeladera(heladera);
            prestacion.setMicroondas(microondas);
            prestacion.setPileta(pileta);
            prestacion.setSeAceptanMascotas(seAceptanMascotas);
            prestacion.setTv(tv);
            prestacion.setWiFi(wiFi);

            prestationRepository.save(prestacion);

        }

    }

    private void validate(Integer cantidadPers, Integer cantAuto, Integer cantCuarto, Integer cantBanio) throws MiException {
        if (cantidadPers < 0 || cantAuto < 0 || cantCuarto < 0 || cantBanio < 0) {
            throw new MiException("Debe elegir un valor positivo o cero.");
        }
        if (cantidadPers == null || cantAuto == null || cantCuarto == null || cantBanio == null) {
            throw new MiException("Debe ingresar un valor.");
        }
    }

    @Transactional
    public Prestation getOne(String idPrestation) {
        return prestationRepository.getOne(idPrestation);
    }

    public void deletePrestation(String idPrestation) throws MiException {

        Optional<Prestation> respuesta = prestationRepository.findById(idPrestation);
        if (respuesta.isPresent()) {
            Prestation prestacion = respuesta.get();
            prestationRepository.delete(prestacion);
        } else {
            throw new MiException("No existen prestaciones.");
        }

    }

}
