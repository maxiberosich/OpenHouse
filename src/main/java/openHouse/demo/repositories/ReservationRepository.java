package openHouse.demo.repositories;

import java.util.List;
import openHouse.demo.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

//    @Query("SELECT r FROM Reservation r WHERE r.alta = :alta")
//    public List<Reservation> buscarPorDisponibilidad(@Param("alta") Boolean alta);

}
