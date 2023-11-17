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
    public String registroPropiedad(@PathVariable String id, @RequestParam Double precioBase, @RequestParam String codigoPostal, @RequestParam String direccion,
            @RequestParam String descripcion, ModelMap modelo, MultipartFile[] archivo, @RequestParam String ciudad,
            @RequestParam String tipoPropiedad,
            @RequestParam Integer capMaxPersonas, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaAlta,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaBaja,
            Integer cantidadPers, Integer cantAuto, Integer cantCuarto, Integer cantBanio,
            boolean pileta, boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi,
            boolean tv, boolean barra, boolean seAceptanMascotas, boolean aguaCorriente, boolean cocina,
            boolean heladera, boolean microondas) throws MiException {
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
    public String mostrarPropiedad(@PathVariable String id, HttpSession session, ModelMap model) {
        User usuario = (User) session.getAttribute("usersession");
        model.addAttribute("propiedad", propertyService.getOne(id));
        model.addAttribute("imagenes", propertyService.getOne(id).getImagenes());
        model.addAttribute("imagenesTamanio", propertyService.getOne(id).getImagenes().size());
        model.addAttribute("usuario", usuario);
        model.addAttribute("comentarios", commentService.buscarPorIdPropiedad(id));
        return "propiedad_detalles.html";
    }

    @PostMapping("/detalles/{id}")
    public String mostrarPropiedadD(@PathVariable String id, HttpSession session, ModelMap model) {
        User usuario = (User) session.getAttribute("usersession");
        model.addAttribute("propiedad", propertyService.getOne(id));
        model.addAttribute("usuario", usuario);
        model.addAttribute("imagenes", propertyService.getOne(id).getImagenes());
        model.addAttribute("comentarios", commentService.buscarPorIdPropiedad(id));
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

        modelo.addAttribute("propertys", propiedadesCiudad);

        return "busqueda.html";
    }

    @GetMapping("/buscarSegunPrecio")
    public String buscarSegunPrecio(@RequestParam String minimo, String maximo, ModelMap modelo) {

        List<Property> propiedadesSegunPrecio = propertyService.buscarSegunPrecio(minimo, maximo);

        modelo.addAttribute("propiedadesSegunPrecio", propiedadesSegunPrecio);

        return "busqueda.html";
    }

    @GetMapping("/modificarPropiedad/{idPropiedad}")
    public String modificarPropiedad(ModelMap modelo, @PathVariable String idPropiedad) {
        modelo.put("property", propertyService.getOne(idPropiedad));
        return "modificar_propiedad.html";
    }

    @PostMapping("/modificadoPropiedad/{idProperty}")
    public String modificadoPropiedad(@PathVariable String idProperty, @RequestParam(required = false) Double precioBase,
            @RequestParam(required = false) String codigoPostal, @RequestParam(required = false) String direccion,
            @RequestParam(required = false) String descripcion, ModelMap modelo, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false) String ciudad, @RequestParam(required = false) String tipoPropiedad,
            @RequestParam(required = false) Integer capMaxPersonas, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaAlta,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaBaja,
            @RequestParam(required = false) Integer cantidadPers, @RequestParam(required = false) Integer cantAuto,
            @RequestParam(required = false) Integer cantCuarto, @RequestParam(required = false) Integer cantBanio,
            boolean pileta, boolean asador, boolean cochera, boolean aireAcondicionado, boolean wiFi,
            boolean tv, boolean barra, boolean seAceptanMascotas, boolean aguaCorriente, boolean cocina,
            boolean heladera, boolean microondas) throws MiException {
        try {
            modelo.addAttribute("property", propertyService.getOne(idProperty));

            propertyService.modificarPropiedad(precioBase, idProperty, capMaxPersonas, codigoPostal, direccion, descripcion, archivo, "mendoza",
                    "CASA", fechaAlta, fechaBaja, cantidadPers, cantAuto, cantCuarto, cantBanio, pileta, asador,
                    cochera, aireAcondicionado, wiFi, tv, barra, seAceptanMascotas, aguaCorriente, cocina, heladera, microondas);
            modelo.put("exito", "Propiedad modificada correctamente");

            return "redirect:/";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("precioBase", precioBase);
            modelo.put("codigoPostal", codigoPostal);
            modelo.put("direccion", direccion);
            modelo.put("descripcion", descripcion);
            modelo.put("capMaxPersonas", capMaxPersonas);
            System.out.println("Aqui iega" + ex.getMessage());
            return "modificar_propiedad.html";
        }
    }

}
