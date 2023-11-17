package openHouse.demo.enums;

public enum PropType {
    SELECCIONAR("Seleccionar"),
    CASA("Casa"),
    QUINTA("Quinta"),
    QUINCHO("Quincho"),
    DEPT("Departamento");

    private final String label;

    PropType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
