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
import openHouse.demo.enums.CardCompany;
import openHouse.demo.enums.CardType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private Integer numero;
    private Integer codSeguridad;
    @Enumerated(EnumType.STRING)
    private CardType tipoTarjeta;
    @Enumerated(EnumType.STRING)
    private CardCompany empresa;
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @OneToOne
    private Client cliente;

}
