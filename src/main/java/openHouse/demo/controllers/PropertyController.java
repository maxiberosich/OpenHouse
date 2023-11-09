package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.enums.City;
import openHouse.demo.enums.PropType;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.CommentService;
import openHouse.demo.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propiedad")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private CommentService commentService;

    @GetMapping("/registrarPropiedad")
    public String registrarPropiedad(ModelMap modelo, HttpSession session) {
        User user = (User) session.getAttribute("usersession");
        modelo.put("Cities", City.values());
        modelo.put("Propiedades", PropType.values());
        modelo.put("user", user);
        return "registrar_propiedad.html";
    }

    @PostMapping("/registrarPropiedad/{id}")
    public String registroPropiedad
            (@PathVariable String id, @RequestParam Double precioBase, @RequestParam String codigoPostal, @RequestParam String direccion,
            @RequestParam String descripcion, ModelMap modelo, MultipartFile archivo, @RequestParam String ciudad,
            @RequestParam String tipoPropiedad,
            @RequestParam Integer capMaxPersonas, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaAlta,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaBaja,
            
            
            Integer cantidadPers, Integer cantAuto, Integer cantCuarto, Integer cantBanio,
            boolean pileta, boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi,
            boolean tv, boolean barra, boolean seAceptanMascotas, boolean aguaCorriente, boolean cocina,
            boolean heladera, boolean microondas) {
        try {
            propertyService.crearProperty(precioBase, codigoPostal, direccion, descripcion, id,
                    archivo, ciudad, tipoPropiedad, capMaxPersonas, fechaAlta, fechaBaja,
                    
                    //de aca para abajo son atributos de prestaciones.
                    //int-
                    cantidadPers, cantAuto, cantCuarto, cantBanio,
                    //boolean-
                    pileta, asador, cochera, aireAcondicionado,
                    wiFi, tv, barra, seAceptanMascotas, aguaCorriente, cocina, heladera, microondas);
            modelo.put("exito", "Propiedad cargada correctamente");
            return "redirect:/";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("precioBase", precioBase);
            modelo.put("codigoPostal", codigoPostal);
            modelo.put("direccion", direccion);
            modelo.put("descripcion", descripcion);
            modelo.put("capMaxPersonas", capMaxPersonas);
            return "registrar_propiedad.html";
        }
    }

    @GetMapping("/detalles/{id}")
    public String mostrarPropiedad(@PathVariable String id, ModelMap modelo) {
        modelo.addAttribute("propiedad", propertyService.getOne(id));
        modelo.addAttribute("comentarios", commentService.buscarPorIdPropiedad(id));
        return "propiedad_detalles.html";
    }

    @PostMapping("/detalles/{id}")
    public String mostrarPropiedadD(@PathVariable String id, ModelMap model){
        model.addAttribute("propiedad", propertyService.getOne(id));
        return "propiedad_detalles.html";
    }

    @GetMapping("/buscarPorCP")
    public String buscarPorCodigoPostal(@RequestParam String codigoPostal, ModelMap modelo) {
        List<Property> propiedadesCP = propertyService.buscarPorCodigoPostal(codigoPostal);
        modelo.addAttribute("propertys", propiedadesCP);
        return "busqueda.html";
    }

    @GetMapping("/buscarPorCiudad")
    public String buscarPorCiudad(@RequestParam String ciudad, ModelMap modelo) {

        List<Property> propiedadesCiudad = propertyService.buscarPorCiudad(ciudad);

        modelo.addAttribute("propiedadesCiudad", propiedadesCiudad);

        return "busqueda.html";
    }

    @GetMapping("/buscarSegunPrecio")
    public String buscarSegunPrecio(@RequestParam String minimo, String maximo, ModelMap modelo) {

        List<Property> propiedadesSegunPrecio = propertyService.buscarSegunPrecio(minimo, maximo);

        modelo.addAttribute("propiedadesSegunPrecio", propiedadesSegunPrecio);

        return "busqueda.html";
    }
}
