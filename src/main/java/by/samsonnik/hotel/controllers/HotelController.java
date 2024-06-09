package by.samsonnik.hotel.controllers;

import by.samsonnik.hotel.dao.*;
import by.samsonnik.hotel.dto.HotelDto;
import by.samsonnik.hotel.exceptions.HotelNotFoundResponseException;
import by.samsonnik.hotel.exceptions.HotelWrongObjectException;
import by.samsonnik.hotel.exceptions.HotelWrongParamException;
import by.samsonnik.hotel.models.*;
import by.samsonnik.hotel.services.HotelService;
import by.samsonnik.hotel.utill.HotelHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/property-view")
@Tag(name = "Hotel Controller", description = "This is the main and only controller in this app current moment.  App " +
        "process all data with Hotel Service help (hotelService), and transforms output data to correct form with " +
        "Hotel Handler (hotelHandler). In this moment it works with JSON data format.")
public class HotelController {

    private final HotelHandler hotelHandler;
    private final HotelService hotelService;


    public HotelController(HotelHandler hotelHandler, HotelService hotelService) {
        this.hotelHandler = hotelHandler;
        this.hotelService = hotelService;
    }

    @Operation(
            summary = "This method sends short data about hotel, that match search parameters",
            description = "The method receives just only one of the next parameters: name, brand, city, country, amenities." +
                    "In case, when the parameter was incorrect, the method are sending \"Incorrect parameter\" message"
    )
    @GetMapping("/search")
    public ResponseEntity<?> getHotelsAndSortByParam(@RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "brand", required = false) String brand,
                                                     @RequestParam(name = "city", required = false) String city,
                                                     @RequestParam(name = "country", required = false) String country,
                                                     @RequestParam(name = "amenities", required = false) String amenities) {

        if (name != null) {
            return new ResponseEntity<>(hotelHandler.makeHotelShortResponseWithSortByName(name), HttpStatus.OK);
        }
        if (brand != null) {
            return new ResponseEntity<>(hotelHandler.makeHotelShortResponseWithSortByBrand(brand), HttpStatus.OK);
        }
        if (city != null) {
            ArrayList<HotelDao> hotelDaoList = hotelService.findAllByCityIgnoreCase(city);
            return new ResponseEntity<>(hotelHandler.makeHotelShort(hotelDaoList), HttpStatus.OK);
        }
        if (country != null) {
            ArrayList<HotelDao> hotelDaoList = hotelService.findAllByCountryIgnoreCase(country);
            return new ResponseEntity<>(hotelHandler.makeHotelShort(hotelDaoList), HttpStatus.OK);
        }
        if (amenities != null) {
            ArrayList<HotelDao> hotelDaoList = hotelService.findAllByAmenitiesIgnoreCase(amenities);
            return new ResponseEntity<>(hotelHandler.makeHotelShort(hotelDaoList), HttpStatus.OK);
        } else return new ResponseEntity<>(new HotelWrongParamException(HttpStatus.NOT_FOUND.value(),
                "Incorrect parameter"), HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "This is histogram method and it sends data, that was group by only one parameter like " +
                    "\"Brand, City, Country, Amenities \"",
            description = "Like in another methods it uses Responce Entity class, and gives back error message in case," +
                    "when the parameter was incorrect")
    @GetMapping("/histogram/{param}")
    public ResponseEntity<?> getHistogramByParam(@PathVariable(value = "param") String param) {
        if (param.equalsIgnoreCase("brand")) {
            return new ResponseEntity<>(hotelService.getHistogramByBrand(), HttpStatus.OK);
        }
        if (param.equalsIgnoreCase("city")) {
            return new ResponseEntity<>(hotelService.getHistogramByCity(), HttpStatus.OK);
        }
        if (param.equalsIgnoreCase("country")) {
            return new ResponseEntity<>(hotelService.getHistogramByCountry(), HttpStatus.OK);
        }
        if (param.equalsIgnoreCase("amenities")) {
            return new ResponseEntity<>(hotelService.getHistogramByAmenities(), HttpStatus.OK);
        } else return new ResponseEntity<>(new HotelWrongParamException(HttpStatus.BAD_REQUEST.value(),
                "Your param is wrong"), HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "It sends to customers data about all hotels",
            description = "There are just little bit of all information about a hotel, and Hotel Handler (HotelHandler)" +
                    "performs output information transformation to correct visible form"
    )
    @GetMapping("/hotels")
    public List<HotelShort> getHotels() {
        return hotelHandler.makeHotelShortResponse();
    }

    @Operation(
            summary = "This gives more detail information about just only one hotel by Id",
            description = "It uses Hotel Handlers help(HotelHandler) to making correct output data (full form) without" +
                    "useless for users technical details"
    )
    @GetMapping("/hotels/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable String id) {
        int intId = Integer.parseInt(id);
        if (hotelService.getHotelById(intId) != null) {
            HotelDao hotel = hotelService.getHotelById(intId);
            HotelFull hotelFull = hotelHandler.makeHotelFullResponse(hotel);
            return new ResponseEntity<>(hotelFull, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HotelNotFoundResponseException(HttpStatus.NOT_FOUND.value(),
                    "This hotel isn't exist"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Amenities adding method",
            description = "In case, when the hotel has current amenities, this method will add amenities without " +
                    "amenities, that were previous in this hotel"
    )
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<?> addAmenitiesToHotel(@PathVariable String id, @RequestBody String[] amenities,
                                                 BindingResult bindingResult) {
        int intId = Integer.parseInt(id);
        if ((hotelService.getHotelById(intId) != null) && (!bindingResult.hasErrors())) {
            hotelService.addAmenities(amenities, intId);
            return new ResponseEntity<>("Your amenities were added!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Your amenities were not added!", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "New Hotel's creating method",
            description = "Method creates new hotels, and in success case sends back short information about hotel," +
                    "which was added. This method is not set property \"Amenities\", and by default sets value " +
                    "\"NO_AMENITIES\". You can set amenities property by method \"addAmenitiesToHotel\", post request," +
                    "url \"/hotels/{id}/amenities\"")
    @PostMapping("/hotels")
    public ResponseEntity<?> createNewHotel(@RequestBody HotelDto hotelDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new HotelWrongObjectException(HttpStatus.BAD_REQUEST.value(),
                    "Your object isn't correct"), HttpStatus.BAD_REQUEST);
        }
        HotelDao hotelDao = hotelService.convertHotelDtoAndSaveData(hotelDto);
        HotelShort hotelShort = hotelHandler.makeHotelShort(hotelDao);
        return new ResponseEntity<>(hotelShort, HttpStatus.OK);
    }
}
