package com.prgrms.broong.management.park.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.prgrms.broong.management.park.converter.ParkConverter;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.LocationDto;
import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;
import com.prgrms.broong.management.park.repository.ParkRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkServiceImplTest {

    private static final Long LOCATION_ID = 1L;
    private static final String CITY_ID = "1";
    private static final String TOWN_ID = "101";
    private static final String LOCATION_NAME = "도봉구";
    private static final Long PARK_ID = 1L;
    private static final Integer POSSIBLE_NUM = 11;

    @InjectMocks
    private ParkServiceImpl parkService;

    @Mock
    private ParkRepository parkRepository;

    @Mock
    private ParkConverter parkConverter;

    private Park park;

    @BeforeEach
    void setUp() {
        Location location = Location.builder()
            .cityId(CITY_ID)
            .townId(TOWN_ID)
            .locationName(LOCATION_NAME)
            .build();

        park = Park.builder()
            .id(PARK_ID)
            .possibleNum(10)
            .location(location)
            .build();
    }

    @DisplayName("주차장 생성 테스트")
    @Test
    void saveCarTest() {
        //given
        ParkRequestDto parkRequestDto = ParkRequestDto.builder()
            .possibleNum(POSSIBLE_NUM)
            .locationDto(
                LocationDto.builder()
                    .id(LOCATION_ID)
                    .cityId(CITY_ID)
                    .townId(TOWN_ID)
                    .locationName(LOCATION_NAME)
                    .build())
            .build();
        given(parkConverter.parkToEntity(parkRequestDto)).willReturn(park);
        given(parkRepository.save(any(Park.class))).willReturn(park);

        //when
        Long userId = parkService.savePark(parkRequestDto);

        //then
        assertThat(userId).isEqualTo(park.getId());
    }

    @DisplayName("주차장 조회 테스트")
    @Test
    void getParkTest() {
        //given
        ParkResponseDto parkResponseDto = ParkResponseDto.builder()
            .possibleNum(POSSIBLE_NUM)
            .locationDto(
                LocationDto.builder()
                    .id(LOCATION_ID)
                    .cityId(CITY_ID)
                    .townId(TOWN_ID)
                    .locationName(LOCATION_NAME)
                    .build())
            .build();
        given(parkRepository.findById(PARK_ID)).willReturn(Optional.of(park));
        given(parkConverter.parkToResponseDto(park)).willReturn(parkResponseDto);

        //when
        ParkResponseDto getParkResponseDto = parkService.getParkById(PARK_ID);

        //then
        assertThat(getParkResponseDto, samePropertyValuesAs(parkResponseDto));
    }

    @DisplayName("주차장 변경 테스트")
    @Test
    void editParkTest() {
        //given
        ParkUpdateDto parkUpdateDto = ParkUpdateDto.builder()
            .possibleNum(POSSIBLE_NUM)
            .build();
        given(parkRepository.findById(anyLong())).willReturn(Optional.of(park));

        //when
        Long editedPark = parkService.editPark(park.getId(), parkUpdateDto);

        //then
        then(parkRepository).should().findById(PARK_ID);
        assertThat(editedPark).isEqualTo(PARK_ID);
    }

}