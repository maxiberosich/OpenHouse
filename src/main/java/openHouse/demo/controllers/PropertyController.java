package openHouse.demo.controllers;

import java.util.List;
import openHouse.demo.entities.Property;
import openHouse.demo.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/propiedad")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    
    @GetMapping("/buscarPorCP")
    public String buscarPorCodigoPostal(@RequestParam String codigoPostal, ModelMap modelo) {
        List<Property> propiedadesCP = propertyService.buscarPorCodigoPostal(codigoPostal);
        modelo.addAttribute("propiedadesCP", propiedadesCP);
        return "busqueda.html";
    }
    
    @GetMapping("/buscarPorCiudad")
    public String buscarPorCiudad(@RequestParam String ciudad, ModelMap modelo){
        
        List<Property> propiedadesCiudad = propertyService.buscarPorCiudad(ciudad);
        
        modelo.addAttribute("propiedadesCiudad", propiedadesCiudad);
        
        return "busqueda.html";
    }
    
    @GetMapping("/buscarSegunPrecio")
    public String buscarSegunPrecio(@RequestParam String minimo, String maximo, ModelMap modelo){
        
        List<Property> propiedadesSegunPrecio = propertyService.buscarSegunPrecio(minimo, maximo);
        
        modelo.addAttribute("propiedadesSegunPrecio", propiedadesSegunPrecio);
        
        return "busqueda.html";
    }
}
