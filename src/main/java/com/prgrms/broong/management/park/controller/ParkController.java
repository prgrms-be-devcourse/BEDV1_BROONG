package com.prgrms.broong.management.park.controller;

import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;
import com.prgrms.broong.management.park.service.ParkService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class ParkController {

    private static final String PARK_ID = "parkId";

    private final ParkService parkService;

    @PostMapping("/parks")
    public ResponseEntity<Map<String, Long>> savePark(
        @Valid @RequestBody ParkRequestDto parkRequestDto) {
        return ResponseEntity.ok(Map.of(PARK_ID, parkService.savePark(parkRequestDto)));
    }

    @GetMapping("/parks/{parkId}")
    public ResponseEntity<ParkResponseDto> getByParkId(@PathVariable("parkId") Long parkId) {
        return ResponseEntity.ok(parkService.getParkById(parkId));
    }

    @PutMapping("/parks/{parkId}")
    public ResponseEntity<Map<String, Long>> editPark(@PathVariable("parkId") Long parkId,
        @Valid @RequestBody
            ParkUpdateDto parkUpdateDto) {
        return ResponseEntity.ok(Map.of(PARK_ID, parkService.editPark(parkId, parkUpdateDto)));
    }

}
