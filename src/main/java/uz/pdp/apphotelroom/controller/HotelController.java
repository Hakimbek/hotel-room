package uz.pdp.apphotelroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apphotelroom.entity.Hotel;
import uz.pdp.apphotelroom.repository.HotelRepository;

import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public Page<Hotel> getAll(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return hotelRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Hotel getOne(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElse(null);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            hotelRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return "Error";
        }
    }

    @PostMapping
    public String add(@RequestBody Hotel hotel) {
        if (hotelRepository.existsByName(hotel.getName())) {
            return "Hotel already exist";
        }
        hotelRepository.save(hotel);
        return "Saved";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()) {
            return "Hotel not found";
        }
        Hotel editedHotel = optionalHotel.get();

        if (hotelRepository.existsByNameAndIdNot(hotel.getName(), editedHotel.getId())) {
            return "Hotel name already exist";
        }

        editedHotel.setName(hotel.getName());
        editedHotel.setStars(hotel.getStars());
        hotelRepository.save(editedHotel);
        return "Edited";
    }
}
