package by.samsonnik.hotel.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "contacts")
@Schema(description = "Hotel contacts entity scheme, this data is locating in the database")
public class ContactsDao {

    @Id
    @Column(name = "id")
    @Schema(description = "Contact id value", example = "2")
    private Integer id;

    @Column(name = "hotel_id")
    @Schema(description = "This is the hotel id, which owns this contacts data", example = "44")
    private Integer hotelId;

    @Column(name = "phone")
    @Schema(description = "This is the phone number", example = "+375295655414")
    private String phone;

    @Column(name = "email")
    @Schema(description = "This is the email", example = "voldemar@mail.ru")
    private String email;
}
