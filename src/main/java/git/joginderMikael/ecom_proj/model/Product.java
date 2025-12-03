package git.joginderMikael.ecom_proj.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data //lombok automatically gets you constructor, getters and setters
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String brand;
    private Double price;
    private String category;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private Date releaseDate;
    private Boolean productAvailable;
    private Integer stockQuantity;

    private String imageName;
    private String imageType;

    @Lob
    private byte[] imageData;

}
