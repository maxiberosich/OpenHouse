package openHouse.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Card;
import openHouse.demo.entities.Client;
import openHouse.demo.enums.CardCompany;
import openHouse.demo.enums.CardType;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.CardRepository;
import openHouse.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public void crear(String nombre, Integer numero, Date vencimiento, Integer codSeguridad, String idCliente) throws MiException {
        validar(nombre, numero, vencimiento, codSeguridad, idCliente);
        Optional<Client> respuestaCliente = clientRepository.findById(idCliente);
        Client cliente = clientRepository.findById(idCliente).get();
        if (respuestaCliente.isPresent()) {
            cliente = respuestaCliente.get();
        }
        Card card = new Card();
        card.setNombre(nombre);
        card.setNumero(numero);
        card.setTipoTarjeta(CardType.CREDITO);
        card.setEmpresa(CardCompany.VISA);
        card.setVencimiento(vencimiento);
        card.setCodSeguridad(codSeguridad);
        card.setCliente(cliente);
        cardRepository.save(card);
    }

    @Transactional
    public void actualizar(String idTarjeta, String nombre, Integer numero, Date vencimiento, Integer codSeguridad, String idCliente) throws MiException {
        validar(nombre, numero, vencimiento, codSeguridad, idCliente);
        Optional<Card> respuesta = cardRepository.findById(idTarjeta);
        Optional<Client> respuestaCliente = clientRepository.findById(idCliente);
        Client cliente = new Client();
        if (respuestaCliente.isPresent()) {
            cliente = respuestaCliente.get();
        }
        if (respuesta.isPresent()) {
            Card card = respuesta.get();
            card.setNombre(nombre);
            card.setNumero(numero);
            card.setTipoTarjeta(CardType.CREDITO);
            card.setEmpresa(CardCompany.VISA);
            card.setVencimiento(vencimiento);
            card.setCodSeguridad(codSeguridad);
            card.setCliente(cliente);
            cardRepository.save(card);
        }
    }

    @Transactional
    public void cambiarTipoDeTarjeta(String id) {
        Optional<Card> respuesta = cardRepository.findById(id);
        if (respuesta.isPresent()) {
            Card card = respuesta.get();
            if (card.getTipoTarjeta().equals(CardType.CREDITO)) {
                card.setTipoTarjeta(CardType.DEBITO);
            } else if (card.getTipoTarjeta().equals(CardType.DEBITO)) {
                card.setTipoTarjeta(CardType.CREDITO);
            }
        }
    }

    @Transactional
    public void cambiarCompania(String id) {
        Optional<Card> respuesta = cardRepository.findById(id);
        if (respuesta.isPresent()) {
            Card card = respuesta.get();
            switch (card.getEmpresa()) {
                case AMERICAN_EXPRESS ->
                    card.setEmpresa(CardCompany.AMERICAN_EXPRESS);
                case DINERS_CLUB ->
                    card.setEmpresa(CardCompany.DINERS_CLUB);
                case MAESTRO ->
                    card.setEmpresa(CardCompany.MAESTRO);
                case MASTERCARD ->
                    card.setEmpresa(CardCompany.MASTERCARD);
                case VISA ->
                    card.setEmpresa(CardCompany.VISA);
                case VISA_DEBITO ->
                    card.setEmpresa(CardCompany.VISA_DEBITO);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Card> listarTarjetas() {
        List<Card> cards = new ArrayList();
        cards = cardRepository.findAll();
        return cards;
    }

    public Card getOne(String id) {
        return cardRepository.getOne(id);
    }

    public void validar(String nombre, Integer numero, Date vencimiento, Integer codSeguridad, String idCliente) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede quedar nulo o vacío");
        }
        if (numero == null) {
            throw new MiException("El número de tarjeta no puede quedar nulo");
        }
        if (vencimiento == null) {
            throw new MiException("Por favor debe indicar la fecha de vencimiento");
        }
        if (codSeguridad == null) {
            throw new MiException("El código de seguridad no puede quedar nulo");
        }
        if (idCliente.isEmpty() || idCliente == null) {
            throw new MiException("El/la cliente/a no debe quedar nulo/a o vacío/a");
        }
    }

}
