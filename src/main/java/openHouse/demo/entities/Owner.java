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

public class Owner extends User{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;
    
    private String cbu;
    
    @ManyToOne
    private List<Property> propiedades;
    
}