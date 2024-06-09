package by.samsonnik.hotel.dto;

import by.samsonnik.hotel.models.Address;
import by.samsonnik.hotel.models.ArrivalTime;
import by.samsonnik.hotel.models.Contacts;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
@Component
public class HotelDto {

    private String name;
    private String description;
    private String brand;
    private Address address;
    private Contacts contacts;
    private ArrivalTime arrivalTime;
}
