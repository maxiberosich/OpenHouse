package openHouse.demo.controllers;

import java.util.Date;
import java.util.List;
import openHouse.demo.entities.Card;
import openHouse.demo.entities.Client;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.CardService;
import openHouse.demo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Client> clients = clientService.listClient();
        modelo.addAttribute("clients", clients);
        return "";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam Integer numero, @RequestParam Date vencimiento,
            @RequestParam Integer codSeguridad, @RequestParam String idCliente, ModelMap modelo) {
        try {
            cardService.crear(nombre, numero, vencimiento, codSeguridad, idCliente);
            modelo.put("exito", "La tarjeta fue cargada exitosamente");
        } catch (MiException ex) {
            List<Client> clients = clientService.listClient();
            modelo.addAttribute("clients", clients);
            modelo.put("error", ex.getMessage());
            return "";
        }
        return "";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Card> cards = cardService.listarTarjetas();
        modelo.addAttribute("cards", cards);
        return "";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("card", cardService.getOne(id));
        List<Card> cards = cardService.listarTarjetas();
        modelo.addAttribute("cards", cards);
        return "";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(String idTarjeta, String nombre, Integer numero, Date vencimiento,
            Integer codSeguridad, String idCliente, ModelMap modelo) {
        try {
            List<Card> cards = cardService.listarTarjetas();
            modelo.addAttribute("cards", cards);
            cardService.cambiarTipoDeTarjeta(idTarjeta);
            cardService.cambiarCompania(idTarjeta);
            cardService.actualizar(idTarjeta, nombre, numero, vencimiento, codSeguridad, idCliente);
            return "";
        } catch (MiException ex) {
            List<Client> clients = clientService.listClient();
            modelo.addAttribute("clients", clients);
            modelo.put("error", ex.getMessage());
            return "";
        }
    }

}
