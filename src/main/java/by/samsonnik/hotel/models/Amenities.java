package by.samsonnik.hotel.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
@Component
public class Amenities {
    private ArrayList<String> amenities;
}
