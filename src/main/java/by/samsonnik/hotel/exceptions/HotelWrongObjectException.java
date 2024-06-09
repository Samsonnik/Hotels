package by.samsonnik.hotel.exceptions;

import lombok.Data;

@Data
public class HotelWrongObjectException {

    private Integer status;
    private String message;

    public HotelWrongObjectException(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
