package by.samsonnik.hotel.exceptions;

import lombok.Data;

@Data
public class HotelNotFoundResponseException {

    private Integer status;
    private String message;

    public HotelNotFoundResponseException(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
