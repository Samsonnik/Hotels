package by.samsonnik.hotel.repositories;

import by.samsonnik.hotel.dao.AmenitiesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AmenitiesRepository extends JpaRepository<AmenitiesDao, Integer> {
    AmenitiesDao findByHotelId(Integer hotelId);
    ArrayList<AmenitiesDao> findAllByAmenitiesContainingIgnoreCase(String amenities);
}
