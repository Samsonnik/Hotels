package by.samsonnik.hotel.services;

import by.samsonnik.hotel.dao.*;
import by.samsonnik.hotel.dto.HotelDto;
import by.samsonnik.hotel.exceptions.HotelIsEmptyException;
import by.samsonnik.hotel.exceptions.HotelNotFoundException;
import by.samsonnik.hotel.repositories.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelService implements HotelServiceInterface {

    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;

    private final AmenitiesRepository amenitiesRepository;
    private final ContactsRepository contactsRepository;

    private final ArrivalTimeRepository arrivalTimeRepository;


    public HotelService(HotelRepository hotelRepository, AddressRepository addressRepository, AmenitiesRepository amenitiesRepository, ContactsRepository contactsRepository, ArrivalTimeRepository arrivalTimeRepository) {
        this.hotelRepository = hotelRepository;

        this.addressRepository = addressRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.contactsRepository = contactsRepository;
        this.arrivalTimeRepository = arrivalTimeRepository;
    }


    @Override
    public HotelDao addHotel(HotelDao hotel) {
        if ((hotel.getId() > 0) && (!hotel.getName().equals(" ")) && (!hotel.getBrand().equals(" "))) {
            return hotelRepository.save(hotel);
        } else {
            throw new HotelIsEmptyException("Hotel or his parameters cannot be a null or \" \"");
        }
    }

    @Override
    public HotelDao updateHotel(HotelDao hotel) {
        Optional<HotelDao> currentHotel = hotelRepository.findById(hotel.getId());
        if (currentHotel.isPresent()) {
            hotelRepository.deleteById(hotel.getId());
            return hotelRepository.save(hotel);
        } else {
            throw new HotelNotFoundException(HotelNotFoundException.DEFAULT_MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public List<HotelDao> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public HotelDao getHotelById(int id) {
        Optional<HotelDao> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new HotelNotFoundException(HotelNotFoundException.DEFAULT_MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void removeHotel(int id) {
        Optional<HotelDao> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            hotelRepository.deleteById(id);
        } else {
            throw new HotelNotFoundException(HotelNotFoundException.DEFAULT_MESSAGE_NOT_FOUND);
        }
    }

    public ArrayList<HotelDao> findAllByContainNameContainingIgnoreCase(String name) {
        return hotelRepository.findAllByNameContainingIgnoreCase(name);
    }

    public ArrayList<HotelDao> findAllByContainBrandContainingIgnoreCase(String brand) {
        return hotelRepository.findAllByBrandContainingIgnoreCase(brand);
    }

    public ArrayList<HotelDao> findAllByCityIgnoreCase(String city) {
        ArrayList<HotelDao> hotelDaoList = new ArrayList<>();
        ArrayList<AddressDao> addressesList = addressRepository.findAllByCityEqualsIgnoreCase(city);
        addressesList.forEach(address -> {
            if (address.getCity().equalsIgnoreCase(city)) {
                HotelDao hotelDao = getHotelById(address.getHotelId());
                hotelDaoList.add(hotelDao);
            }
        });
        return hotelDaoList;
    }

    public ArrayList<HotelDao> findAllByCountryIgnoreCase(String country) {
        ArrayList<HotelDao> hotelDaoList = new ArrayList<>();
        ArrayList<AddressDao> addressesList = addressRepository.findAllByCountryEqualsIgnoreCase(country);
        addressesList.forEach(address -> {
            if (address.getCountry().equalsIgnoreCase(country)) {
                HotelDao hotelDao = getHotelById(address.getHotelId());
                hotelDaoList.add(hotelDao);
            }
        });
        return hotelDaoList;
    }

    public ArrayList<HotelDao> findAllByAmenitiesIgnoreCase(String amenities) {
        ArrayList<HotelDao> hotelDaoList = new ArrayList<>();
        ArrayList<AmenitiesDao> amenitiesDaoList = amenitiesRepository.findAllByAmenitiesContainingIgnoreCase(amenities);
        amenitiesDaoList.forEach(amenitiesDao -> hotelDaoList.add(hotelRepository.findById(amenitiesDao.getHotelId()).get()));
        return hotelDaoList;
    }

    public HotelDao convertHotelDtoAndSaveData(HotelDto hotelDto) {
        Integer hotelId = getRandomNumber();

        HotelDao hotelDao = new HotelDao();
        hotelDao.setId(hotelId);
        hotelDao.setName(hotelDto.getName());
        hotelDao.setBrand(hotelDto.getBrand());
        hotelDao.setDescription(hotelDto.getDescription());
        hotelRepository.save(hotelDao);

        Integer addressId = getRandomNumber();
        AddressDao addressDao = new AddressDao();
        addressDao.setId(addressId);
        addressDao.setHotelId(hotelId);
        addressDao.setHouseNumber(hotelDto.getAddress().getHouseNumber());
        addressDao.setStreet(hotelDto.getAddress().getStreet());
        addressDao.setCity(hotelDto.getAddress().getCity());
        addressDao.setCountry(hotelDto.getAddress().getCountry());
        addressDao.setPostCode(hotelDto.getAddress().getPostCode());
        addressRepository.save(addressDao);

        Integer contactsId = getRandomNumber();
        ContactsDao contactsDao = new ContactsDao();
        contactsDao.setId(contactsId);
        contactsDao.setHotelId(hotelId);
        contactsDao.setEmail(hotelDto.getContacts().getEmail());
        contactsDao.setPhone(hotelDto.getContacts().getPhone());
        contactsRepository.save(contactsDao);

        Integer arrivalTimeId = getRandomNumber();
        ArrivalTimeDao arrivalTimeDao = new ArrivalTimeDao();
        arrivalTimeDao.setId(arrivalTimeId);
        arrivalTimeDao.setHotelId(hotelId);
        arrivalTimeDao.setCheckIn(hotelDto.getArrivalTime().getCheckIn());
        arrivalTimeDao.setCheckOut(hotelDto.getArrivalTime().getCheckOut());
        arrivalTimeRepository.save(arrivalTimeDao);

        AmenitiesDao amenitiesDao = new AmenitiesDao();
        SplittableRandom splittableRandom = new SplittableRandom();
        amenitiesDao.setId(splittableRandom.nextInt(0, Integer.MAX_VALUE));
        amenitiesDao.setHotelId(hotelId);
        amenitiesDao.setAmenities("NO_AMENITIES");
        amenitiesRepository.save(amenitiesDao);
        return hotelDao;
    }

    public void addAmenities(String[] amenities, Integer hotelId) {
        AmenitiesDao amenitiesDao = amenitiesRepository.findByHotelId(hotelId);
        if (amenitiesDao.getAmenities().equalsIgnoreCase("NO_AMENITIES")) {
            String amenitiesString = Arrays.toString(amenities);
            amenitiesDao.setAmenities(amenitiesString.substring(1, amenitiesString.length() - 1));
            amenitiesRepository.save(amenitiesDao);
        } else {
            String[] oldAmenities = amenitiesDao.getAmenities().split(", ");
            HashSet<String> newAmenities = new HashSet<>();
            Collections.addAll(newAmenities, amenities);
            Collections.addAll(newAmenities, oldAmenities);
            String finalAmenities = newAmenities.toString();
            amenitiesDao.setAmenities(finalAmenities.substring(1, finalAmenities.length() - 1));
            amenitiesRepository.save(amenitiesDao);
        }
    }

    public HashMap<String, Integer> getHistogramByBrand() {
        HashMap<String, Integer> histogram = new HashMap<>();
        hotelRepository.findAll().forEach(hotelDao -> {
            if (histogram.containsKey(hotelDao.getBrand())) {
                int counter = histogram.get(hotelDao.getBrand());
                counter++;
                histogram.put(hotelDao.getBrand(), counter);
            } else histogram.put(hotelDao.getBrand(), 1);
        });
        return histogram;
    }

    public HashMap<String, Integer> getHistogramByCity() {
        HashMap<String, Integer> histogram = new HashMap<>();
        addressRepository.findAll().forEach(addressDao -> {
            if (histogram.containsKey(addressDao.getCity())) {
                int counter = histogram.get(addressDao.getCity());
                counter++;
                histogram.put(addressDao.getCity(), counter);
            } else histogram.put(addressDao.getCity(), 1);
        });
        return histogram;
    }

    public HashMap<String, Integer> getHistogramByCountry() {
        HashMap<String, Integer> histogram = new HashMap<>();
        addressRepository.findAll().forEach(addressDao -> {
            if (histogram.containsKey(addressDao.getCountry())) {
                int counter = histogram.get(addressDao.getCountry());
                counter++;
                histogram.put(addressDao.getCountry(), counter);
            } else histogram.put(addressDao.getCountry(), 1);
        });
        return histogram;
    }

    public HashMap<String, Integer> getHistogramByAmenities() {
        HashMap<String, Integer> histogram = new HashMap<>();
        amenitiesRepository.findAll().forEach(amenitiesDao -> {
            String[] amenitiesArr = amenitiesDao.getAmenities().split(", ");
            Arrays.stream(amenitiesArr)
                    .forEach(amenity -> {
                        if (histogram.containsKey(amenity)) {
                            int counter = histogram.get(amenity);
                            counter++;
                            histogram.put(amenity, counter);
                        } else histogram.put(amenity, 1);
                    });
        });
        return histogram;
    }

    private Integer getRandomNumber() {
        int min = 0;
        int max = Integer.MAX_VALUE;
        SplittableRandom splittableRandom = new SplittableRandom();
        return splittableRandom.nextInt(min, max);
    }
}
