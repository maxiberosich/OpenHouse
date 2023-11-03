package openHouse.demo.enums;

public enum City {
    MENDOZA("mendoza"),
    CORDOBA("cordoba"),
    NEUQUEN("neuquen"),
    BUENOS_AIRES("buenos aires"),
    SANTA_FE("santa fe"),
    SAN_JUAN("san juan");
    
    
    private final String label;

    City(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    } 
}
