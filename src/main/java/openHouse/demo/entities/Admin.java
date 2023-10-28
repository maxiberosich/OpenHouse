package openHouse.demo.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
public class Admin extends User {

    public Admin() {
        super();
    }
}
