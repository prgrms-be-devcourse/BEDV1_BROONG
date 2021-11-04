package com.prgrms.broong.management.park.controller;

import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;
import com.prgrms.broong.management.park.service.ParkService;
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
@RequestMapping(path = "/api")
public class ParkController {

    private final ParkService parkService;

    @PostMapping("/v1/broong/parks")
    public ResponseEntity<Long> savepark(@Valid @RequestBody ParkRequestDto parkRequestDto) {
        return ResponseEntity.ok(parkService.savePark(parkRequestDto));
    }

    @GetMapping("/v1/broong/parks/{parkId}")
    public ResponseEntity<ParkResponseDto> getByParkId(@PathVariable("parkId") Long parkId) {
        return ResponseEntity.ok(parkService.getParkById(parkId));
    }

    @PutMapping("/v1/broong/parks/{parkId}")
    public ResponseEntity<Long> editPark(@PathVariable("parkId") Long parkId, @Valid @RequestBody
        ParkUpdateDto parkUpdateDto) {
        return ResponseEntity.ok(parkService.editPark(parkId, parkUpdateDto));
    }

}
