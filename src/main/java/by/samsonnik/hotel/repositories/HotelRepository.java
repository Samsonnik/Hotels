package by.samsonnik.hotel.repositories;

import by.samsonnik.hotel.dao.HotelDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface HotelRepository extends JpaRepository<HotelDao, Integer> {
    ArrayList<HotelDao> findAllByNameContainingIgnoreCase(String name);

    ArrayList<HotelDao> findAllByBrandContainingIgnoreCase(String brand);
}
