package openHouse.demo.repositories;

import java.util.List;
import openHouse.demo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("SELECT c FROM Comment c WHERE c.propiedad.id = :idPropiedad")
    public List<Comment> buscarPorIdPropiedad(@Param("idPropiedad") String idPropiedad);
}
