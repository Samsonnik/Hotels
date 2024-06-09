package by.samsonnik.hotel.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "arrivaltime")
@Schema(description = "Hotel arrival time entity scheme, this data is locating in the database")
public class ArrivalTimeDao {

    @Id
    @Column(name = "id")
    @Schema(description = "Arrival time id value", example = "2")
    private Integer id;

    @Column(name = "hotel_id")
    @Schema(description = "This is the hotel id, which owns this arrival time data", example = "44")
    private Integer hotelId;

    @Column(name = "checkin")
    @Schema(description = "This is the date time (In)", example = "11:00")
    private String checkIn;

    @Column(name = "checkout")
    @Schema(description = "This is the date time (Out)", example = "17:00")
    private String checkOut;
}
