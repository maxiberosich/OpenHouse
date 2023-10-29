package openHouse.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Client extends User {
    
    private boolean alta;
    //private List <tarjetas>;
    // falta crear entidad "tarjeta" que esta como sugerencia en trello.  
    
    @ManyToOne
    private Reservation reservaActiva;
    
    /*@OneToMany
    private List<Property> propiedades;*/

    public Client() {
        super();
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public Reservation getReservaActiva() {
        return reservaActiva;
    }

    public void setReservaActiva(Reservation reservaActiva) {
        this.reservaActiva = reservaActiva;
    }

    @Override
    public String toString() {
        return "Client{" + "alta=" + alta + ", reservaActiva=" + reservaActiva + '}';
    }

}
