package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.ErrorPropertyRequestException;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.repository.NeighborhoodRepository;
import com.github.transformeli.desafio_quality.repository.PropertyRepository;
import com.github.transformeli.desafio_quality.util.TestUtilsNeighborhood;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import java.util.HashSet;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertyServiceTest {

    @InjectMocks
    PropertyService service;
    @Mock
    PropertyRepository repository;
    @Mock
    NeighborhoodRepository neighborhoodRepository;

    @BeforeEach
    void setup() {
        BDDMockito
                .when(repository.create(ArgumentMatchers.any(Property.class)))
                .thenReturn(TestUtilsProperty.getNewProperty());

        BDDMockito
                .when(repository.update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Property.class)))
                .thenReturn(TestUtilsProperty.getNewProperty());

        BDDMockito
                .when(repository.delete(ArgumentMatchers.anyString()))
                .thenReturn(true);

        BDDMockito
                .when(neighborhoodRepository.findByKey(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(TestUtilsNeighborhood.getNewNeighborhood()));
    }

    /**
     * test room total area when room exist
     * @author Larissa Navarro
     */
    @Test
    void roomTotalArea_returnArea_whenRoomExists() {
        Room room = TestUtilsProperty.getNewRoom();

        Double result = service.roomTotalArea(room);

        assertThat(result).isPositive();
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(6D);
    }

    /**
     * test room total area when room not exist
     * @author Larissa Navarro
     */
    @Test
    void roomTotalArea_returnArea_whenRoomNotExists() {
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            Double result = service.roomTotalArea(null);
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    /**
     * test biggest room when room exist
     * @author Larissa Navarro
     */
    @Test
    void propBiggestRoom_returnBiggestRoom_WhenRoomExist() {
        Property prop = TestUtilsProperty.getNewProperty();

        Room result = service.propBiggestRoom(prop);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Sala");
    }

    /**
     * test biggest room when room not exist
     * @author Larissa Navarro
     */
    @Test
    void propBiggestRoom_returnException_WhenRoomNotExist() {
        Property prop = TestUtilsProperty.getNewProperty();
        prop.setRooms(new HashSet<>());

        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            Room result = service.propBiggestRoom(prop);
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * test price by neighborhood when neighborhood  exist
     * @author Larissa Navarro
     */
    @Test
    void propPriceByNeighborhood_returnValue_WhenNeighborhoodExists() {
        Property prop = TestUtilsProperty.getNewProperty();

        Double result = service.propPriceByNeighborhood(prop);
        assertThat(result).isEqualTo(9225D);
        assertThat(result).isPositive();

    }

    /**
     * test price by neighborhood when neighborhood not exist
     * @author Larissa Navarro
     */
    @Test
    void propPriceByNeighborhood_returnException_WhenPropertyNotExists() {
        Property prop = TestUtilsProperty.getNewProperty();
        prop.setNeighborhood(null);
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            Double result = service.propPriceByNeighborhood(prop);
        });
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    /**
     * test property total area  when room not exist
     * @author Larissa Navarro
     */
    @Test
    void propTotalArea_returnTotal_whenPropertyRoomExists() {
        Property prop = TestUtilsProperty.getNewProperty();
        Double result = service.propTotalArea(prop);
        assertThat(result).isNotNull();
        assertThat(result).isPositive();
        assertThat(result).isEqualTo(615D);
    }

    /**
     * test property total area, return NotFoundException  when room not exist
     * @author Larissa Navarro
     */
    @Test
    void propTotalArea_returnException_whenPropertyRoomNotExists() {
        Property prop = TestUtilsProperty.getNewProperty();
        prop.setRooms(new HashSet<>());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            Double result = service.propTotalArea(prop);
        });
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    /**
     * test create property
     * @author Larissa Navarro
     */
    @Test
    void createNewProperty_returnProperty_whenPropertyExists() {

        Property prop = TestUtilsProperty.getNewProperty();
        Property propSaved = service.createNewProperty(prop);
        assertThat(prop).isEqualTo(propSaved);
        assertThat(propSaved).isNotNull();
        verify(repository, atLeastOnce()).create(prop);
    }

    /**
     * test create property  return Exception Error when object already exist
     * @author Larissa Navarro
     */
    @Test
    void createNewProperty_returnExceptionError_whenPropertyAlreadyExists() {
        BDDMockito.when(repository.create(ArgumentMatchers.any(Property.class))).thenReturn(null);
        Property prop = TestUtilsProperty.getNewProperty();
        ErrorPropertyRequestException ex = Assertions.assertThrows(ErrorPropertyRequestException.class, () -> {
            service.createNewProperty(prop);
        });
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * test update property  when property exists
     * @author Larissa Navarro
     */
    @Test
    void updateProperty_returnProperty_whenPropertyExists() {
        BDDMockito.when(repository.update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Property.class)))
                .thenReturn(TestUtilsProperty.buildProperty("Teste123", "Penha", TestUtilsProperty.buildRooms()));
        Property prop = TestUtilsProperty.getNewProperty();
        Property propSaved = service.createNewProperty(prop);
        propSaved.setName("Teste123");

        Property propModify = service.updateProperty(prop.getName(), propSaved);

        assertThat(propModify).isNotNull();
        assertThat(propModify.getName()).isEqualTo("Teste123");
        assertThat(propModify).isNotEqualTo(prop);
    }

    /**
     * test update property  return Exception Error when object not exist
     * @author Larissa Navarro
     */
    @Test
    void updateProperty_returnErrorException_whenPropertyNotExists() {
        BDDMockito.when(repository.update(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(Property.class))).thenReturn(null);
        Property prop = TestUtilsProperty.getNewProperty();
        ErrorPropertyRequestException ex = Assertions.assertThrows(ErrorPropertyRequestException.class, () -> {
            service.updateProperty(prop.getName(), prop);
        });
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * test delete property  when object already exist
     * @author Larissa Navarro
     */
    @Test
    void deleteProperty_whenPropertyExists() {
        Property prop = TestUtilsProperty.getNewProperty();
        Property create = service.createNewProperty(prop);
        service.deleteProperty(create.getName());
        verify(repository, atLeastOnce()).delete(prop.getName());
    }

    /**
     * test delete property  return Not found exception when object not exist
     * @author Larissa Navarro
     */
    @Test
    void deleteProperty_whenPropertyNotExists() {
        BDDMockito.when(repository.delete(ArgumentMatchers.anyString())).thenThrow(new NotFoundException("teste"));
        Property prop = TestUtilsProperty.getNewProperty();
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            service.deleteProperty(prop.getName());
        });
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}
