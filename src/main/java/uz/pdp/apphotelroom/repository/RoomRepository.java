package uz.pdp.apphotelroom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apphotelroom.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);

    boolean existsByNumberAndHotelId(Integer number, Integer hotel_id);

    boolean existsByNumberAndHotelIdAndIdNot(Integer number, Integer hotel_id, Integer id);
}
