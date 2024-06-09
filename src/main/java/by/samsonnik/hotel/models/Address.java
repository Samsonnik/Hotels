package by.samsonnik.hotel.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Data
@Component
public class Address {
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
