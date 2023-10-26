
package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import openHouse.demo.Enum.Rol;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
public abstract class User {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;
    
    private Rol rol;
    private String name;
    private String password;
    private String Dni;
    private String phone;
    private Date birthdate;
    private boolean state;
    
    @OneToOne
    private Image image;

}
