package openHouse.demo.enums;

public enum PropType {
    CASA("casa"),
    QUINTA("quinta"), 
    QUINCHO("quincho"),
    DEPT("departamento");
    
    private final String label;

    PropType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    
}
