package by.samsonnik.hotel.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Contacts {
    private String phone;
    private String email;
}
