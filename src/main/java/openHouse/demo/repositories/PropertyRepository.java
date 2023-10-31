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
    
    @Query("SELECT p FROM Property p WHERE p.ciudad LIKE '%:ciudad%'")
    public List<Property> buscarPorCiudad(@Param("ciudad") String ciudad);
    
    @Query("SELECT p FROM Property p WHERE p.precioBase BETWEEN :minimo AND :maximo")
    public List<Property> buscarSegunPrecio(@Param("minimo") String minimo, @Param("maximo") String maximo);
    
}
