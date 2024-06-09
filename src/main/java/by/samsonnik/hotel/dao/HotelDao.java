package by.samsonnik.hotel.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hotel")
@Schema(description = "Hotel entity scheme, this data is locating in the database")
public class HotelDao {
    @Id
    @Column(name = "id")
    @Schema(description = "hotel id value", example = "2")
    private Integer id;

    @Column(name = "name")
    @Schema(description = "This is the name of the hotel", example = "Continental")
    private String name;

    @Column(name = "brand")
    @Schema(description = "This is the brand of the hotel", example = "Continental ltd.")
    private String brand;

    @Column(name = "description")
    @Schema(description = "This is the short description of the hotel")
    private String description;

    public HotelDao() {
    }

    public HotelDao(Integer id, String name, String brand, String description) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
    }
}

