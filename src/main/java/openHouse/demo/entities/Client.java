package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Client extends User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private boolean alta;
    //private List <tarjetas>;
    // falta crear entidad "tarjeta" que esta como sugerencia en trello.  
    
    @ManyToOne
    private Reservation reservaActiva;
    

    @ManyToOne
    private List<Property> propiedades;
}
