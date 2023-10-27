package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.Data;
import openHouse.demo.enums.City;
import openHouse.demo.enums.PropType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Property {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;

    @OneToOne
    private Prestation prestaciones;
    @OneToOne
    private List<Image> imagenes;
    @OneToMany
    private Owner propietario;
    
    @Enumerated(EnumType.STRING)
    private City ciudad;

    @ManyToOne
    private List<Comment> comentarios;

    @Enumerated(EnumType.STRING)
    private PropType tipo;

    private Double precioBase;
    private String codigoPostal;
    private String direccion;
    private String descripcion;
    private Double valoracion;
    private Boolean alta;
    
    
    //CALENDARIO
    //@Temporal(TemporalType.DATE)
    //private Date fechaAlta;
    
    //@Temporal(TemporalType.DATE)
    //private Date fechaBaja;
    
}
