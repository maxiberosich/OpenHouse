
package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Reservation {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private boolean alta;
    private Date fechaInicio;
    private Date fechaFin;
    
    @OneToOne
    private Client cliente;
    private Double precioFinal;
    private Integer cantPersonas;
    
    @OneToOne
    private Property propiedad;
             
}
