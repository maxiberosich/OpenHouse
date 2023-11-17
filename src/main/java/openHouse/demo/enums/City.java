package openHouse.demo.enums;

public enum City {
    SELECCIONAR("Seleccionar"),
    MENDOZA("Mendoza"),
    CORDOBA("Córdoba"),
    NEUQUEN("Neuquén"),
    BUENOS_AIRES("Buenos Aires"),
    SANTA_FE("Santa Fe"),
    SAN_JUAN("San Juan");
    
    
    private final String label;

    City(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    } 
}
