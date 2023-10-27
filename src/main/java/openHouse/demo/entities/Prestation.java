package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Prestation {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name= "uuid", strategy="uuid2")
    private String Id;
    
    private Integer cantidadPers;
    private Integer cantAuto;
    private Integer cantCuarto;
    private Integer cantBanio;
    private boolean pileta;
    private boolean asador;
    private boolean cochera;    
    private boolean aireAcondicionado;    
    private boolean wiFi;
    private boolean tv;
    private boolean barra;
    private boolean seAceptanMascotas;
    private boolean aguaCorriente;
    private boolean cocina;
    private boolean heladera;
    private boolean microondas;
}
