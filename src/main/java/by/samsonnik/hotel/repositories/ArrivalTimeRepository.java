package by.samsonnik.hotel.repositories;

import by.samsonnik.hotel.dao.ArrivalTimeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArrivalTimeRepository extends JpaRepository<ArrivalTimeDao, Integer> {
    ArrivalTimeDao findByHotelId(Integer hotelId);
}
