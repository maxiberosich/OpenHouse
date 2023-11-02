package openHouse.demo.repositories;

import openHouse.demo.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    @Query("SELECT c FROM Card c WHERE c.numero = :number")
    public Card buscarPorNumTarjeta(@Param("number") Integer number);

}
