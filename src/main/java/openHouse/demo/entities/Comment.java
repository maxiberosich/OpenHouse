package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String cuerpo;
    private Double valoracion;
    
    @OneToOne
    private Image imagen;
    
    //OneToOne
    @ManyToOne
    private Property propiedad;
    
    @ManyToOne
    private Client cliente;

}
