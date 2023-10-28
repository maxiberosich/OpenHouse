package openHouse.demo.services;

import java.util.Date;
import java.util.Optional;
import openHouse.demo.entities.Image;
import openHouse.demo.entities.Owner;
import openHouse.demo.enums.Rol;
import openHouse.demo.exception.MiException;
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
        
        validar(name, password, password2, email, dni, phone, birthdate);
        Owner propietario = new Owner();
        propietario.setName(name);
        propietario.setPassword(new BCryptPasswordEncoder().encode(password));
        propietario.setEmail(email);
        propietario.setDni(dni);
        propietario.setPhone(phone);
        propietario.setBirthdate(birthdate);
        propietario.setCbu(cbu);
        propietario.setRol(Rol.PROPIETARIO);
        Image imagen = imageService.save(archivo);
        
        propietario.setImage(imagen);
        
        propietarioRepositorio.save(propietario);
    }
    
    @Transactional
    public void modificarPropietario(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, String cbu, MultipartFile archivo, String idPropietario) throws MiException {
        
        validar(name, password, password2, email, dni, phone, birthdate);
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
            Date birthdate) throws MiException {
        
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
        if (dni == null) {
            throw new MiException("Tiene que indicar el DNI, por favor.");
        }
        if (phone == null) {
            throw new MiException("Tiene que indicar el numero de telefono , por favor.");
        }
        if (birthdate == null) {
            throw new MiException("Por favor debe indicar su fecha de nacimiento!.");
        }
    }
    
}