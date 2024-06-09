package by.samsonnik.hotel.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ArrivalTime {
    private String checkIn;
    private String checkOut;
}
