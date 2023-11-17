package openHouse.demo.controllers;

import java.util.Date;
import java.util.List;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Owner;
import openHouse.demo.entities.Property;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.ClientService;
import openHouse.demo.services.CommentService;
import openHouse.demo.services.OwnerService;
import openHouse.demo.services.PropertyService;
import openHouse.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private CommentService commentService;

    @GetMapping("/dashboard")
    public String adminPanel(ModelMap modelo) {
        List<Property> propiedades = propertyService.listaPropiedades();
        modelo.addAttribute("propertys", propiedades);
        return "redirect:/";
    }

    @GetMapping("/clientes")
    public String listarClientes(ModelMap modelo) {
        List<Client> clientes = clientService.listClient();
        modelo.addAttribute("clientes", clientes);

        return "cliente_list.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/propietarios")
    public String listarPropietarios(ModelMap modelo) {
        List<Owner> propietarios = ownerService.listaPropietarios();
        modelo.addAttribute("propietarios", propietarios);

        return "propietario_list.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modificarCliente/{id}")
    public String modificarCliente(ModelMap modelo, @PathVariable String id) {
        modelo.put("cliente", clientService.getOne(id));

        return "modificar_cliente.html";
    }

    @PostMapping("/modificarCliente/{id}")
    public String modificarCliente(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, MultipartFile archivo, String idClient, ModelMap modelo) {

        try {
            clientService.update(name, password, password2, email, dni, phone, birthdate, archivo, idClient);

            modelo.put("exito", "Cliente actualizado correctamente");

            return "cliente_list.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("name", name);
            modelo.put("email", email);
            modelo.put("dni", dni);
            modelo.put("phone", phone);
            
            return "modificar_cliente.html";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modificarPropietario/{id}")
    public String modificarPropietario(ModelMap modelo, @PathVariable String id) {
        modelo.put("propietario", ownerService.getOne(id));

        return "modificarpropietario.html";
    }
    
    @PostMapping("/modificarPropietario/{id}")
    public String modificarPropietario(ModelMap modelo, String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, String cbu, MultipartFile archivo, String idPropietario){
        
        try {
            ownerService.modificarPropietario(name, password, password2, email, dni, phone, birthdate, cbu, archivo, idPropietario);
            
            modelo.put("exito", "Propietario actualizado correctamente");
            
            return "propietario_list.html";
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("name",name);
            modelo.put("email",email);
            modelo.put("dni",dni);
            modelo.put("phone",phone);
            modelo.put("cbu",cbu);
            
            return "modificarpropietario.html";
        }
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN','CLIENTE')")
    @GetMapping("modificarAltaCliente/{id}")
    public String cambiarAltaCliente(@PathVariable String id){
        Client cliente = clientService.getOne(id);
        clientService.bajaCliente(id);
        if(cliente.getRol() == cliente.getRol().CLIENTE){
            return "inicio.html";
        }else{
            return "redirect:/admin/clientes";
        }
        
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("modificarAltaPropietario/{id}")
    public String cambiarAltaPropietario(@PathVariable String id){
        ownerService.bajaPropietario(id);
        
        return "redirect:/admin/propietarios";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("eliminarCliente/{id}")
    public String eliminarCliente(@PathVariable String id){
        clientService.elimnarClient(id);
        
        return "redirect:/admin/clientes";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("eliminarComentario/{id}")
    public String eliminarComentario(@PathVariable String id){
        commentService.elimnarComment(id);
        
        return "redirect:/";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("eliminarPropietario/{id}")
    public String eliminarPropietario(@PathVariable String id){
        ownerService.eliminarPropietario(id);
        
        return "redirect:/admin/propietarios";
    }
}
