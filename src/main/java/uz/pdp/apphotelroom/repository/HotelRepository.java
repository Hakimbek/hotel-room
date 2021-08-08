package uz.pdp.apphotelroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apphotelroom.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
