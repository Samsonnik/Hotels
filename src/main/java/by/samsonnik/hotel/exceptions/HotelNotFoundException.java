package by.samsonnik.hotel.exceptions;

import lombok.Data;
public class HotelNotFoundException extends RuntimeException{

    public static final String  DEFAULT_MESSAGE_NOT_FOUND = "This hotel isn't exist";
    public HotelNotFoundException(String message) {
        super(message);
    }
}
