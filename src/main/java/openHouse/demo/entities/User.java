package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Data;

import openHouse.demo.enums.Rol;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public abstract class User {
        
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    private String name;
    private String password;
    private String Dni;
    private String phone;
    
    private String email;
    
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    
    private boolean state;
    
    @OneToOne
    private Image image;

}
