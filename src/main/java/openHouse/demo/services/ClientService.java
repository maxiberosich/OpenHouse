package openHouse.demo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Image;
import openHouse.demo.entities.Property;
import openHouse.demo.enums.Rol;
import openHouse.demo.repositories.ClientRepository;
import openHouse.demo.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepo;
    
    @Autowired
    private ReservationRepository reservationRepo;
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void createClient(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate,MultipartFile archivo){
        
        Client cliente = new Client();
        
        cliente.setRol(Rol.CLIENTE);
        cliente.setName(name);
        cliente.setEmail(email);
        cliente.setBirthdate(birthdate);
        cliente.setPassword(new BCryptPasswordEncoder().encode(password));
        cliente.setDni(dni);
        cliente.setPhone(phone);
        
        Image imagen = imageService.save(archivo);
        
        cliente.setImage(imagen);
        
        clientRepo.save(cliente);
    }
    
    
}
