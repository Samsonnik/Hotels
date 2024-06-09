package by.samsonnik.hotel.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
@Table(name = "amenities")
@Schema(description = "Hotel amenities entity scheme, this data is locating in the database")
public class AmenitiesDao {

    @Id
    @Column(name = "id")
    @Schema(description = "Amenities id value", example = "2")
    private Integer id;

    @Schema(description = "This is the hotel id, which owns this amenities data", example = "44")
    @Column(name = "hotel_id")
    private Integer hotelId;

    @Schema(description = "This are the amenities, which the hotel has", example = "Free WiFi")
    @Column(name = "amenities")
    private String amenities;
}
