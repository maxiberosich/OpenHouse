package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
//data de lombok ------viene con los get and setter y to sting.Constructores tambien vacio y completo.
@Entity
@Data
public class Admin extends User {

}
