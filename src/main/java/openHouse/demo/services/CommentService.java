package openHouse.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Comment;
import openHouse.demo.entities.Image;
import openHouse.demo.exception.MiException;
import openHouse.demo.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageService imageService;

    public void create(MultipartFile archivo, String cuerpo, Double valoracion) throws MiException {
//        Optional<Property> answerProperty;
        Comment comment = new Comment();
        comment.setCuerpo(cuerpo);
        comment.setValoracion(valoracion);
        Image imagen = imageService.save(archivo);
        comment.setImagen(imagen);
        commentRepository.save(comment);
    }

    public void modify(String idComment, MultipartFile archivo, String cuerpo, Double valoracion) throws MiException {
        Optional<Comment> answer = commentRepository.findById(idComment);
        if (answer.isPresent()) {
            Comment comment = answer.get();
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

}
