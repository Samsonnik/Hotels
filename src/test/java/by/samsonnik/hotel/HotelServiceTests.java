package by.samsonnik.hotel;

import by.samsonnik.hotel.dao.HotelDao;
import by.samsonnik.hotel.exceptions.HotelNotFoundException;
import by.samsonnik.hotel.services.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HotelServiceTests {
    private final HotelService hotelService;

    @Autowired
    public HotelServiceTests(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Test
    public void testAddHotelAndGetHotelById() {
        HotelDao savedHotel = new HotelDao(1, "Hotel", "Brand", "description");
        hotelService.addHotel(savedHotel);
        assertEquals(hotelService.getHotelById(savedHotel.getId()), savedHotel);

    }

    @Test
    public void testUpdateHotel() {
        HotelDao savedHotel = new HotelDao(1, "Hotel", "Brand", "description");
        hotelService.addHotel(savedHotel);
        savedHotel.setName("UpdatedHotel");
        hotelService.updateHotel(savedHotel);
        assertEquals(hotelService.getHotelById(savedHotel.getId()).getName(), "UpdatedHotel");
    }

    @Test
    public void testRemoveHotel() {
        HotelDao savedHotel = new HotelDao(1, "Hotel", "Brand", "description");
        hotelService.addHotel(savedHotel);
        hotelService.removeHotel(savedHotel.getId());
        HotelNotFoundException exception = assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(1));
        assertEquals("This hotel isn't exist", exception.getMessage());
    }
}
