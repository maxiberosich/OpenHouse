package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.Data;


import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "propiedad")
public class Property {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;

    @OneToOne
    private Prestation prestaciones;

    @OneToMany
    private List<Image> imagenes;

    @ManyToOne
    private Owner propietario;

    private String ciudad;

    @OneToMany
    private List<Comment> comentarios;

    private String tipo;

    private Double precioBase;
    private String codigoPostal;
    private String direccion;
    private String descripcion;
    private Double valoracion;
    private Boolean alta;
    private Boolean permitidoFiestas;
    private Integer capMaxPersonas;

    //CALENDARIO
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

}
