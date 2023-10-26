package openHouse.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
=======
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
>>>>>>> developer
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@ToString
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mime;
    private String name;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

}
