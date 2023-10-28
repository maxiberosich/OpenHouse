package openHouse.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Comment;
import openHouse.demo.entities.Image;
import openHouse.demo.entities.Property;
import openHouse.demo.exception.MiException;
import openHouse.demo.repositories.CommentRepository;
import openHouse.demo.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ImageService imageService;

    public void create(MultipartFile archivo, String idPropiedad, String cuerpo, Double valoracion) throws MiException {
        validar(cuerpo, valoracion);
        Optional<Property> respuestaPropiedad = propertyRepository.findById(idPropiedad);
        Property propiedad = propertyRepository.findById(idPropiedad).get();
        if (respuestaPropiedad.isPresent()) {
            propiedad = respuestaPropiedad.get();
        }
        Comment comment = new Comment();
        comment.setPropiedad(propiedad);
        comment.setCuerpo(cuerpo);
        comment.setValoracion(valoracion);
        Image imagen = imageService.save(archivo);
        comment.setImagen(imagen);
        commentRepository.save(comment);
    }

    public void modify(String idComment, String idPropiedad, MultipartFile archivo, String cuerpo, Double valoracion) throws MiException {
        validar(cuerpo, valoracion);
        Optional<Property> respuestaPropiedad = propertyRepository.findById(idPropiedad);
        Optional<Comment> respuesta = commentRepository.findById(idComment);
        Property propiedad = new Property();
        if (respuestaPropiedad.isPresent()) {
            propiedad = respuestaPropiedad.get();
        }
        if (respuesta.isPresent()) {
            Comment comment = respuesta.get();
            comment.setPropiedad(propiedad);
            comment.setCuerpo(cuerpo);
            comment.setValoracion(valoracion);
            Image imagen = imageService.save(archivo);
            comment.setImagen(imagen);
            commentRepository.save(comment);
        }
    }

    public List<Comment> list() {
        List<Comment> comments = new ArrayList();
        comments = commentRepository.findAll();
        return comments;
    }

    public void delete(String id) {
        commentRepository.deleteById(id);
    }

    public Comment getOne(String id) {
        return commentRepository.getOne(id);
    }
    
    private void validar(String cuerpo, Double valoracion) throws MiException {
        if (valoracion == null) {
            throw new MiException("No se puede dejar una valoración nula");
        }
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("No se puede dejar un comentario en blanco");
        }
    }

}
