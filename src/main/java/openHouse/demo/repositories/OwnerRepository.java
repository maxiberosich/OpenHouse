package openHouse.demo.repositories;

import openHouse.demo.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {
    
    @Query("SELECT e FROM Owner e WHERE e.email = :email")
    public Owner buscarPorEmail (@Param("email" )String email);
    
    @Query("SELECT e FROM Owner e WHERE e.name = :name")
    public Owner buscarPorNombre (@Param("name") String name); 
}
