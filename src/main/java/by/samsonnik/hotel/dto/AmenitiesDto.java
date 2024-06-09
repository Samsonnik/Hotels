package by.samsonnik.hotel.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class AmenitiesDto {
    private List<String> amenities;
}
