package by.samsonnik.hotel.services;

import by.samsonnik.hotel.dao.HotelDao;

import java.util.List;

public interface HotelServiceInterface {

    HotelDao addHotel(HotelDao hotels);

    HotelDao updateHotel(HotelDao hotels);

    List<HotelDao> getAllHotels();

    HotelDao getHotelById(int id);

    void removeHotel(int id);
}
