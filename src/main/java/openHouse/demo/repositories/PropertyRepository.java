package openHouse.demo.repositories;

import java.util.List;
import openHouse.demo.entities.Property;
import openHouse.demo.enums.PropType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

    @Query("SELECT p FROM Property p WHERE p.codigoPostal = :codigoPostal")
    public List<Property> buscarPorCodigoPostal(@Param("codigoPostal") String codigoPostal);

    @Query("SELECT p FROM Property p WHERE p.ciudad = :ciudad")
    public List<Property> buscarPorCiudad(@Param("ciudad") String ciudad);
    
    @Query("SELECT p FROM Property p WHERE p.propietario.id = :idPropietario")
    public List<Property> buscarPorPropietario(@Param("idPropietario") String idPropietario);
    
    @Query("SELECT p FROM Property p WHERE p.tipo = :tipo")
    public List<Property> buscarPorTipo(@Param("tipo") PropType tipo);

//    @Query("SELECT p FROM Property p WHERE p.alta = :alta")
//    public List<Property> buscarPorDisponibilidad(@Param("alta") Boolean alta);
    
    @Query("SELECT p FROM Property p WHERE p.precioBase BETWEEN :minimo AND :maximo")
    public List<Property> buscarSegunPrecio(@Param("minimo") String minimo, @Param("maximo") String maximo);

    @Query("SELECT p FROM Property p WHERE p.valoracion = :valoracion")
    public List<Property> buscarPorValoracion(@Param("valoracion") Double valoracion);

}
