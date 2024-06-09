package by.samsonnik.hotel;


import by.samsonnik.hotel.controllers.HotelController;
import by.samsonnik.hotel.dao.HotelDao;
import by.samsonnik.hotel.dto.HotelDto;
import by.samsonnik.hotel.models.HotelFull;
import by.samsonnik.hotel.models.HotelShort;
import by.samsonnik.hotel.services.HotelService;
import by.samsonnik.hotel.utill.HotelHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HotelControllerTests {

	private ArrayList<HotelShort> hotelDtoShortDataResponse;
	@Mock
	private HotelService hotelService;
	@Mock
	private HotelHandler hotelHandler;

	@BeforeEach
	public void setDataForTesting() {
		String jsonFilePathShort = "src/test/java/by/samsonnik/hotel/resources/dataForTestingShortData.json";
		File jsonFileShortData = new File(jsonFilePathShort);
		ObjectMapper mapper = new ObjectMapper();
		try {
			hotelDtoShortDataResponse = mapper.readValue(jsonFileShortData, new TypeReference<>() {});
		} catch (IOException exception) {
			throw new RuntimeException(exception.getMessage());
		}
	}

	@Test
	void testGetHotels() {
		Mockito.when(hotelHandler.makeHotelShortResponse()).thenReturn(hotelDtoShortDataResponse);
		HotelController hotelController = new HotelController(hotelHandler, hotelService);
		assertEquals(hotelController.getHotels(), hotelDtoShortDataResponse);
	}

	@Test
	void testGetHotelById() {
		String id = "1";
		HotelDao hotelDao = new HotelDao();
		HotelFull hotelFull = new HotelFull();
		Mockito.when(hotelService.getHotelById(Integer.parseInt(id))).thenReturn(hotelDao);
		Mockito.when(hotelHandler.makeHotelFullResponse(hotelDao)).thenReturn(hotelFull);
		HotelController hotelController = new HotelController(hotelHandler, hotelService);
		assertEquals(hotelController.getHotelById(id), new ResponseEntity<>(hotelFull, HttpStatus.OK));
	}
	@Test
	void testGetHotelsAndSortByParam() {
		String name = "name";
		Mockito.when(hotelHandler.makeHotelShortResponseWithSortByName(name)).thenReturn(hotelDtoShortDataResponse);
		HotelController hotelController = new HotelController(hotelHandler, hotelService);
		assertEquals(hotelController.getHotelsAndSortByParam(name, name, name, name, name),
				new ResponseEntity<>(hotelDtoShortDataResponse, HttpStatus.OK));
	}
	@Test
	void testGetHistogramByParam() {
		String param = "brand";
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("hilton", 2);
		Mockito.when(hotelService.getHistogramByBrand()).thenReturn(hashMap);
		HotelController hotelController = new HotelController(hotelHandler, hotelService);
		assertEquals(hotelController.getHistogramByParam(param),
				new ResponseEntity<>(hashMap, HttpStatus.OK));
	}
	@Test
	void testAddAmenitiesToHotel() {
		String id = "2";
		String[] amenities = {"wifi", "fre tv"};
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		HotelDao hotelDao = new HotelDao();
		Mockito.when(hotelService.getHotelById(Integer.parseInt(id))).thenReturn(hotelDao);
		HotelController hotelController = new HotelController(hotelHandler, hotelService);
		assertEquals(hotelController.addAmenitiesToHotel(id, amenities, bindingResult),
				new ResponseEntity<>("Your amenities were added!", HttpStatus.OK));
	}
	@Test
	void testCreateNewHotel() {
		HotelDto hotelDto = new HotelDto();
		HotelDao hotelDao = new HotelDao();
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(hotelService.convertHotelDtoAndSaveData(hotelDto)).thenReturn(hotelDao);
		Mockito.when(hotelHandler.makeHotelShort(hotelDao)).thenReturn(hotelDtoShortDataResponse.get(0));
		HotelController hotelController = new HotelController(hotelHandler, hotelService);
		assertEquals(hotelController.createNewHotel(hotelDto, bindingResult),
				new ResponseEntity<>(hotelDtoShortDataResponse.get(0), HttpStatus.OK));
	}
}
