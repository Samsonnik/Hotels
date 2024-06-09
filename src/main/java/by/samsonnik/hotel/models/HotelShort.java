package by.samsonnik.hotel.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class HotelShort {
    private Integer id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
