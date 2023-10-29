package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;


@Entity
@Table(name = "propietario")
public class Owner extends User{

    private String cbu;
    
    @OneToMany
    private List<Property> propiedades;

    public Owner() {
        super();
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public List<Property> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List<Property> propiedades) {
        this.propiedades = propiedades;
    }

    @Override
    public String toString() {
        return "Owner{" + "cbu=" + cbu + ", propiedades=" + propiedades + '}';
    }
    
}