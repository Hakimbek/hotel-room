package uz.pdp.apphotelroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apphotelroom.entity.Hotel;
import uz.pdp.apphotelroom.entity.Room;
import uz.pdp.apphotelroom.payload.RoomDto;
import uz.pdp.apphotelroom.repository.HotelRepository;
import uz.pdp.apphotelroom.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/byHotel/{hotelId}")
    public Page<Room> getRoomByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAllByHotelId(hotelId, pageable);
    }

    @GetMapping("/byRoomId/{roomId}")
    public Room getOne(@PathVariable Integer roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        return optionalRoom.orElse(null);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            roomRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return "Error";
        }
    }

    @PostMapping
    public String add(@RequestBody RoomDto roomDto) {
        if (roomRepository.existsByNumberAndHotelId(roomDto.getNumber(), roomDto.getHotelId())) {
            return "Room already exist";
        }

        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) {
            return "Hotel not found";
        }
        Hotel hotel = optionalHotel.get();

        room.setHotel(hotel);
        roomRepository.save(room);
        return "Saved";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) {
            return "Room not found";
        }
        Room room = optionalRoom.get();

        if (roomRepository.existsByNumberAndHotelIdAndIdNot(roomDto.getNumber(), roomDto.getHotelId(), room.getId())) {
            return "Room already exist";
        }

        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        roomRepository.save(room);
        return "Edited";
    }
}
