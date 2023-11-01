package openHouse.demo.repositories;

import openHouse.demo.entities.ServicioExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioExtraRepository extends JpaRepository<ServicioExtra, String>{
    
}
