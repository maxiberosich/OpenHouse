package openHouse.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import openHouse.demo.entities.Image;
import openHouse.demo.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image save(MultipartFile file) {
        if (file != null) {
            try {
                Image image = new Image();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Image update(MultipartFile file, String idImage) {
        if (file != null) {
            try {
                Image image = new Image();
                if (idImage != null) {
                    Optional<Image> answer = imageRepository.findById(idImage);
                    if (answer.isPresent()) {
                        image = answer.get();
                    }
                }
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Image> list() {
        return imageRepository.findAll();
    }

}
