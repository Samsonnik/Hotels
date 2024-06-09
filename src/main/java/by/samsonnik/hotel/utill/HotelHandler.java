package by.samsonnik.hotel.utill;

import by.samsonnik.hotel.dao.*;
import by.samsonnik.hotel.models.*;
import by.samsonnik.hotel.repositories.AddressRepository;
import by.samsonnik.hotel.repositories.AmenitiesRepository;
import by.samsonnik.hotel.repositories.ArrivalTimeRepository;
import by.samsonnik.hotel.repositories.ContactsRepository;
import by.samsonnik.hotel.services.HotelService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class HotelHandler {

    private final HotelService hotelService;
    private final AddressRepository addressRepository;
    private final AmenitiesRepository amenitiesRepository;
    private final ArrivalTimeRepository arrivalTimeRepository;
    private final ContactsRepository contactsRepository;


    public HotelHandler(HotelService hotelService, AddressRepository addressRepository, AmenitiesRepository amenitiesRepository, ArrivalTimeRepository arrivalTimeRepository, ContactsRepository contactsRepository) {
        this.hotelService = hotelService;
        this.addressRepository = addressRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.arrivalTimeRepository = arrivalTimeRepository;
        this.contactsRepository = contactsRepository;
    }

    public ArrayList<HotelShort> makeHotelShortResponse() {
        return makeHotelShort((ArrayList<HotelDao>) hotelService.getAllHotels());
    }

    public ArrayList<HotelShort> makeHotelShortResponseWithSortByName(String name) {
        return makeHotelShort(hotelService.findAllByContainNameContainingIgnoreCase(name));
    }

    public ArrayList<HotelShort> makeHotelShortResponseWithSortByBrand(String brand) {
        return makeHotelShort(hotelService.findAllByContainBrandContainingIgnoreCase(brand));
    }

    public HotelFull makeHotelFullResponse(HotelDao hotel) {
        Integer hotelId = hotel.getId();
        AddressDao addressDao = addressRepository.findByHotelId(hotelId);

        AmenitiesDao amenitiesDao = amenitiesRepository.findByHotelId(hotelId);
        ArrivalTimeDao arrivalTimeDao = arrivalTimeRepository.findByHotelId(hotelId);
        ContactsDao contactsDao = contactsRepository.findByHotelId(hotelId);
        HotelFull hotelFull = new HotelFull();
        hotelFull.setId(hotel.getId());
        hotelFull.setName(hotel.getName());
        hotelFull.setBrand(hotel.getBrand());
        hotelFull.setAddress(makeAddress(addressDao));
        hotelFull.setContacts(makeContacts(contactsDao));
        hotelFull.setArrivalTime(makeArrivalTime(arrivalTimeDao));
        hotelFull.setAmenities(makeAmenities(amenitiesDao));
        return hotelFull;
    }

    private Address makeAddress(AddressDao addressDao) {
        Address address = new Address();
        address.setCity(addressDao.getCity());
        address.setCountry(addressDao.getCountry());
        address.setHouseNumber(addressDao.getHouseNumber());
        address.setPostCode(addressDao.getPostCode());
        address.setStreet(addressDao.getStreet());
        return address;
    }

    private String[] makeAmenities(AmenitiesDao amenitiesDao) {
        String line = amenitiesDao.getAmenities();
        return line.split(", ");
    }

    private ArrivalTime makeArrivalTime(ArrivalTimeDao arrivalTimeDao) {
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(arrivalTimeDao.getCheckIn());
        arrivalTime.setCheckOut(arrivalTimeDao.getCheckOut());
        return arrivalTime;
    }

    private Contacts makeContacts(ContactsDao contactsDao) {
        Contacts contacts = new Contacts();
        contacts.setEmail(contactsDao.getEmail());
        contacts.setPhone(contactsDao.getPhone());
        return contacts;
    }

    private String makeFullAddressLine(AddressDao address) {
        return address.getHouseNumber() + ", " +
                address.getStreet() + ", " +
                address.getCity() + ", " +
                address.getPostCode() + ", " +
                address.getCountry();
    }

    public ArrayList<HotelShort> makeHotelShort(ArrayList<HotelDao> listDao) {
        ArrayList<HotelShort> listShort = new ArrayList<>();
        listDao.forEach(hotel -> {
            Integer hotelId = hotel.getId();
            AddressDao address = addressRepository.findByHotelId(hotelId);
            ContactsDao contacts = contactsRepository.findByHotelId(hotelId);
            HotelShort hotelShort = new HotelShort();
            hotelShort.setId(hotel.getId());
            hotelShort.setName(hotel.getName());
            hotelShort.setDescription(hotel.getDescription());
            hotelShort.setAddress(makeFullAddressLine(address));
            hotelShort.setPhone(contacts.getPhone());
            listShort.add(hotelShort);
        });
        return listShort;
    }

    public HotelShort makeHotelShort(HotelDao hotelDao) {
        Integer hotelId = hotelDao.getId();
        AddressDao address = addressRepository.findByHotelId(hotelId);
        ContactsDao contacts = contactsRepository.findByHotelId(hotelId);
        HotelShort hotelShort = new HotelShort();
        hotelShort.setId(hotelDao.getId());
        hotelShort.setName(hotelDao.getName());
        hotelShort.setDescription(hotelDao.getDescription());
        hotelShort.setAddress(makeFullAddressLine(address));
        hotelShort.setPhone(contacts.getPhone());
        return hotelShort;
    }
}
