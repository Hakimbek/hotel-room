package uz.pdp.apphotelroom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Integer number;

    private Integer floor;

    private Double size;

    private Integer hotelId;
}
