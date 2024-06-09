package by.samsonnik.hotel.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address")
@Schema (description = "Hotel address entity scheme, this data is locating in the database")
public class AddressDao {

    @Id
    @Column(name = "id")
    @Schema(description = "Hotel id value", example = "2")
    private Integer id;

    @Schema(description = "This is the hotel id, which owns this address data", example = "44")
    @Column(name = "hotel_id")
    private Integer hotelId;

    @Schema(description = "It is number the house, in  which the hotel is located", example = "11")
    @Column(name = "housenumber")
    private String houseNumber;

    @Schema(description = "It is name of the street, in which the hotel is located", example = "Victory street")
    @Column(name = "street")
    private String street;

    @Schema(description = "It is name of the city, in which the hotel is located", example = "Minsk")
    @Column(name = "city")
    private String city;
    @Schema(description = "It is name of the country, in which the hotel is located", example = "Belarus")
    @Column(name = "country")
    private String country;

    @Schema(description = "It is code of the hotel", example = "321122")
    @Column(name = "postcode")
    private String postCode;
}
