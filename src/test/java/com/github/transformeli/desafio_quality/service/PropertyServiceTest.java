package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty2;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyServiceTest {

    PropertyService service;

    @BeforeEach
    void setup() {
        service = new PropertyService();
    }

    @Test
    void roomTotalArea() {
        Room room = TestUtilsProperty.getNewRoom();

        Double result = service.roomTotalArea(room);

        assertThat(result).isEqualTo(6D);
        assertThat(result).isPositive();
        assertThat(result).isNotNull();

    }

    @Test
    void propTotalArea() {
        Property prop = TestUtilsProperty.getNewProperty();

        Double result = service.propTotalArea(prop);

        assertThat(result).isEqualTo(30D);
    }

    @Test
    void propBiggestRoom() {
        Property prop = TestUtilsProperty2.getNewProperty();

        Room result = service.propBiggestRoom(prop);

        assertThat(result.getName()).isEqualTo("Test 2");
    }

    @Test
    void propPriceByNeighborhood() {
        Property prop = TestUtilsProperty2.getNewProperty();

        Double result = service.propPriceByNeighborhood(prop);

        assertThat(result).isEqualTo(900D);
    }
}
