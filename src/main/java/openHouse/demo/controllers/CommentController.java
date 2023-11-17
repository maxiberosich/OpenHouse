package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.CommentService;
import openHouse.demo.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/comentario")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/registrarComentario/{idPropiedad}")
    public String registrarComentario(ModelMap modelo, HttpSession session, @PathVariable String idPropiedad) {
        User user = (User) session.getAttribute("usersession");
        Property propiedad = propertyService.getOne(idPropiedad);
        modelo.put("user", user);
        modelo.addAttribute("propiedad", propiedad);
        modelo.put("comentarios", propiedad.getComentarios());
        return "/fragments/crear_comentario";
    }

    @PostMapping("/registroCommentario/{idPropiedad}/{idCliente}")
    public String registroComentario(ModelMap modelo, @PathVariable String idCliente,
            @PathVariable String idPropiedad, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam String cuerpo, @RequestParam(required = false) Double Calificacion) {

        try {
            commentService.create(archivo, idPropiedad, cuerpo, Calificacion, idCliente);
            modelo.put("exito", "Reservacion agendada correctamente");
            return "redirect:/propiedad/detalles/{idPropiedad}";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/propiedad/detalles/{idPropiedad}";
        }
    }
}
