package openHouse.demo.repositories;

import java.util.List;
import openHouse.demo.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    
    @Query("SELECT r FROM Reservation r WHERE r.propiedad.id = :idPropiedad")
    public List<Reservation> buscarPorPropiedad(@Param("idPropiedad") String idPropiedad);
    
    @Query("SELECT r FROM Reservation r WHERE r.propiedad.propietario.id = :idPropietario")
    public List<Reservation> listaReservasActivas(@Param("idPropietario") String idPropietario);
}
