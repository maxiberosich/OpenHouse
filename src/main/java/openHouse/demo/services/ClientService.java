package openHouse.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Client;
import openHouse.demo.entities.Image;
import openHouse.demo.entities.Reservation;
import openHouse.demo.enums.Rol;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void createClient(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, MultipartFile archivo) throws MiException, IOException {

        validate(name, password, password2, email, dni, phone, birthdate);

        Client cliente = new Client();

        cliente.setRol(Rol.CLIENTE);
        cliente.setName(name);
        cliente.setEmail(email);
        cliente.setBirthdate(birthdate);
        cliente.setPassword(new BCryptPasswordEncoder().encode(password));
        cliente.setDni(dni);
        cliente.setPhone(phone);
        cliente.setAlta(true);
        Image imagen = new Image();
        if (archivo.isEmpty()) {
            imagen = imageService.save(archivo);
            imagen.setContent(obtenerBytesImagenPredeterminada());
        } else {
            imagen = imageService.save(archivo);
        }

        cliente.setImage(imagen);

        clientRepo.save(cliente);
    }

    public void update(String name, String password, String password2, String email, String dni, String phone,
            Date birthdate, MultipartFile archivo, String idClient) throws MiException {

        validate(name, password, password2, email, dni, phone, birthdate);

        Optional<Client> respuesta = clientRepo.findById(idClient);

        if (respuesta.isPresent()) {

            Client cliente = respuesta.get();

            cliente.setName(name);
            cliente.setPassword(new BCryptPasswordEncoder().encode(password));
            cliente.setEmail(email);
            cliente.setDni(dni);
            cliente.setPhone(phone);
            cliente.setBirthdate(birthdate);
            cliente.setRol(Rol.CLIENTE);

            String idImagen = null;

            if (cliente.getImage() != null) {

                idImagen = cliente.getImage().getId();
            }

            Image imagen = imageService.update(archivo, idImagen);

            cliente.setImage(imagen);

            clientRepo.save(cliente);
        }

    }

    public Client getOne(String id) {
        return clientRepo.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Client> listClient() {

        List<Client> clientes = new ArrayList();
        clientes = clientRepo.findAll();
        return clientes;
    }

    public void elimnarClient(String id) {
        Optional<Client> respuesta = clientRepo.findById(id);

        if (respuesta.isPresent()) {
            Client cliente = respuesta.get();
            clientRepo.delete(cliente);
        }
    }

    public void bajaCliente(String id) {
        Optional<Client> respuesta = clientRepo.findById(id);

        if (respuesta.isPresent()) {
            Client cliente = respuesta.get();
            if (cliente.isAlta() == true) {
                cliente.setAlta(false);
            } else if (cliente.isAlta() == false) {
                cliente.setAlta(true);
            }
            clientRepo.save(cliente);
        }
    }

    public void validate(String name, String password, String password2, String email, String dni, String phone, Date birthdate)
            throws MiException {
        if (name.isEmpty() || name == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio.");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacia o ser menor de 6 digitos");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio.");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas no coinciden");
        }
        if (name.isEmpty() || name == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio.");
        }
        if (dni.isEmpty() || dni == null) {
            throw new MiException("El dni no puede ser nulo o estar vacio.");
        }
        if (dni.length() != 8) {
            throw new MiException("El dni debe contener 8 digitos.");
        }
        if (phone.isEmpty() || phone == null) {
            throw new MiException("El telefono no puede ser nulo o estar vacio.");
        }
        if (phone.length() < 10) {
            throw new MiException("El telefono debe contener al menos 10 numeros.");
        }
        if (birthdate == null) {
            throw new MiException("Por favor debe indicar su fecha de nacimiento!.");
        }
    }

    private byte[] obtenerBytesImagenPredeterminada() throws IOException {
        Resource resource = new ClassPathResource("static/img/OPENHOUSE.png");
        InputStream inputStream = resource.getInputStream();
        return inputStream.readAllBytes();
    }
    
    public void eliminarReserva(String idCliente,String idReserva){
        Optional<Client> clienteRes = clientRepo.findById(idCliente);
        if (clienteRes.isPresent()) {
            Client cliente = clienteRes.get();
            List<Reservation> reservasActivas = cliente.getReservaActiva();
            for (Reservation reserva : reservasActivas) {
                if (reserva.getId().equals(idReserva)) {
                    reservasActivas.remove(reserva);
                    break;
                }
            }
            cliente.setReservaActiva(reservasActivas);
            clientRepo.save(cliente);
        }
        
    }
}
