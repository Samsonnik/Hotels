package by.samsonnik.hotel.repositories;

import by.samsonnik.hotel.dao.AddressDao;
import by.samsonnik.hotel.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AddressRepository extends JpaRepository<AddressDao, Integer> {
    AddressDao findByHotelId(Integer hotelId);
    ArrayList<AddressDao> findAllByCityEqualsIgnoreCase(String city);
    ArrayList<AddressDao> findAllByCountryEqualsIgnoreCase(String country);
}
