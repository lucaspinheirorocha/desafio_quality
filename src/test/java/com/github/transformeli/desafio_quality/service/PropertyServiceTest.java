package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;

class PropertyServiceTest {

    PropertyService service;

    @BeforeEach
    void setup() {
        service = new PropertyService();
    }

    @Test
    void roomTotalArea_returnArea_whenRoomExists() {
        Room room = TestUtilsProperty.getNewRoom();
        Double result = service.roomTotalArea(room);
        assertThat(result).isPositive();
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(6D);
    }

    @Test
    void propBiggestRoom_returnBiggestRoom_WhenRoomExist() {
        Property prop = TestUtilsProperty.getNewProperty();
        Room result = service.propBiggestRoom(prop);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Sala");
    }

    @Test
    void propBiggestRoom_returnException_WhenRoomNotExist() {
        Property prop = TestUtilsProperty.getNewProperty();
        prop.setRooms(new HashSet<>());

        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            Room result = service.propBiggestRoom(prop);
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void propPriceByNeighborhood_returnValue_WhenPropertyExists() {
        Property prop = TestUtilsProperty.getNewProperty();
        Double result = service.propPriceByNeighborhood(prop);
        assertThat(result).isEqualTo(9225D);
        assertThat(result).isPositive();

    }

    @Test
    void propPriceByNeighborhood_returnException_WhenPropertyNotExists() {


    }
}
