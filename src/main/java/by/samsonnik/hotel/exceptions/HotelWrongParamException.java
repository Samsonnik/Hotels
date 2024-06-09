package by.samsonnik.hotel.exceptions;

import lombok.Data;

@Data
public class HotelWrongParamException {

    private Integer status;
    private String message;

    public HotelWrongParamException(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
