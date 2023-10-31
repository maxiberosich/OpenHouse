package openHouse.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Image;
import openHouse.demo.entities.Owner;
import openHouse.demo.enums.Rol;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OwnerService {
    
    @Autowired
    private OwnerRepository propietarioRepositorio;
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void crearPropietario(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, String cbu, MultipartFile archivo) throws MiException {
        
        validar(name, password, password2, email, dni, phone, birthdate,cbu);
        Owner propietario = new Owner();
        propietario.setName(name);
        propietario.setPassword(new BCryptPasswordEncoder().encode(password));
        propietario.setEmail(email);
        propietario.setDni(dni);
        propietario.setPhone(phone);
        propietario.setBirthdate(birthdate);
        propietario.setCbu(cbu);
        propietario.setRol(Rol.PROPIETARIO);
        propietario.setAlta(Boolean.TRUE);
        Image imagen = imageService.save(archivo);
        
        propietario.setImage(imagen);
        
        propietarioRepositorio.save(propietario);
    }
    
    @Transactional
    public void modificarPropietario(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, String cbu, MultipartFile archivo, String idPropietario) throws MiException {
        
        validar(name, password, password2, email, dni, phone, birthdate,cbu);
        Optional<Owner> respuesta = propietarioRepositorio.findById(idPropietario);
        
        if (respuesta.isPresent()) {
            Owner propietario = respuesta.get();
            propietario.setName(name);
            propietario.setPassword(new BCryptPasswordEncoder().encode(password));
            propietario.setEmail(email);
            
            propietario.setPhone(phone);
            
            propietario.setCbu(cbu);
            
            Image imagen = imageService.save(archivo);
            propietario.setImage(imagen);
            propietarioRepositorio.save(propietario);
        }
    }
    
    public void validar(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, String cbu) throws MiException {
        
        if (name == null) {
            throw new MiException("Por favor indicar el nombre!");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacía, y debe tener mas de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("las constraseñas deben ser iguales");
        }
        if (email == null) {
            throw new MiException("Por favor debe insertar un email.");
        }
        if (dni == null || dni.isEmpty()) {
            throw new MiException("Tiene que indicar el DNI, por favor.");
        }
        if (dni.length() != 8) {
            throw new MiException("El dni debe contener 8 digitos.");
        }
        if (phone == null || phone.isEmpty()) {
            throw new MiException("Tiene que indicar el numero de telefono , por favor.");
        }
        if (phone.length() < 10) {
            throw new MiException("El telefono debe contener al menos 10 numeros.");
        }
        if (birthdate == null) {
            throw new MiException("Por favor debe indicar su fecha de nacimiento!.");
        }
        if (cbu == null || cbu.isEmpty() || cbu.length() != 22) {
            throw new MiException("El cbu debe contener 22 numeros.");
        }
    }
    
    public void eliminarPropietario(String id){
        Optional<Owner> respuesta =propietarioRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Owner propietario = respuesta.get();
            propietarioRepositorio.delete(propietario);
        }
    }
    
    public void bajaPropietario(String id){
        Optional<Owner> respuesta =propietarioRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Owner propietario = respuesta.get();
            propietario.setAlta(Boolean.FALSE);
        }
    }
    
    public Owner getOne(String id){
        return propietarioRepositorio.getOne(id);
    }
    
    public List<Owner> listaPropietarios(){
        List<Owner> listaPropietarios=new ArrayList();
        listaPropietarios= propietarioRepositorio.findAll();
        return listaPropietarios;
    }
}
