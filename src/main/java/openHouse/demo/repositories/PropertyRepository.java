package openHouse.demo.repositories;

import java.util.List;
import openHouse.demo.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

    @Query("SELECT p FROM Property p WHERE p.codigoPostal = :codigoPostal")
    public List<Property> buscarPorCodigoPostal(@Param("codigoPostal") String codigoPostal);
    
}
