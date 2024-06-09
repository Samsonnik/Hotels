package by.samsonnik.hotel.repositories;

import by.samsonnik.hotel.dao.ContactsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<ContactsDao, Integer> {
    ContactsDao findByHotelId(Integer hotelId);
}
