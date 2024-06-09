package by.samsonnik.hotel;

import by.samsonnik.hotel.dao.HotelDao;
import by.samsonnik.hotel.dto.HotelDto;
import by.samsonnik.hotel.models.HotelShort;
import by.samsonnik.hotel.services.HotelService;
import by.samsonnik.hotel.utill.HotelHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HotelHandlerTests {

    private final HotelHandler hotelHandler;
    private final HotelService hotelService;
    private static ArrayList<HotelDto> hotelDtoFullDataRequest;
    private static ArrayList<HotelShort> hotelDtoShortDataResponse;


    @Autowired
    public HotelHandlerTests(HotelHandler hotelHandler, HotelService hotelService) {
        this.hotelHandler = hotelHandler;
        this.hotelService = hotelService;
    }

    @BeforeEach
    public void setDataForTesting() {
        String jsonFilePathFull = "src/test/java/by/samsonnik/hotel/resources/dataForTestingFullData.json";
        String jsonFilePathShort = "src/test/java/by/samsonnik/hotel/resources/dataForTestingShortData.json";
        File jsonFileFullData = new File(jsonFilePathFull);
        File jsonFileShortData = new File(jsonFilePathShort);
        ObjectMapper mapper = new ObjectMapper();
        try {
            hotelDtoFullDataRequest = mapper.readValue(jsonFileFullData, new TypeReference<>() {});
            hotelDtoShortDataResponse = mapper.readValue(jsonFileShortData, new TypeReference<>() {});
            assertEquals(hotelDtoFullDataRequest.size(), hotelDtoShortDataResponse.size());
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Test
    void testMakeHotelShortResponse() {
        hotelService.convertHotelDtoAndSaveData(hotelDtoFullDataRequest.get(0));
        ArrayList<HotelShort> resultList = hotelHandler.makeHotelShort((ArrayList<HotelDao>) hotelService.getAllHotels());
        HotelShort expected = hotelDtoShortDataResponse.get(0);
        HotelShort result = resultList.get(0);
        assertNotEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAddress(), result.getAddress());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getPhone(), result.getPhone());
    }
}
