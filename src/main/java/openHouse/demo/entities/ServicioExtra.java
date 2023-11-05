package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class ServicioExtra {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;
    
    private String servicio;
    private String descripcion;
    private Double precio;

}
