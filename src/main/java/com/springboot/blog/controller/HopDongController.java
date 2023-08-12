package com.springboot.blog.controller;

import com.springboot.blog.payload.HopDongDto;
import com.springboot.blog.service.HopDongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hopdong")
public class HopDongController {
    private HopDongService hopDongService;
    public HopDongController(HopDongService hopDongService){
        this.hopDongService = hopDongService;
    }

    @PostMapping
    public ResponseEntity<HopDongDto> addHopDong(@RequestBody HopDongDto hopDongDto){
        return new ResponseEntity<>(hopDongService.addHopDong(hopDongDto), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HopDongDto> getHopDongById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(hopDongService.getHopDongById(id));
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HopDongDto> updateHopDong(@Valid @RequestBody HopDongDto hopDongDto, @PathVariable(name = "id") long id){
        HopDongDto hopDongResponse = hopDongService.updateHopDong(hopDongDto, id);
        return new ResponseEntity<>(hopDongResponse, HttpStatus.OK);
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHopDong(@PathVariable(name = "id") long id){
        hopDongService.deleteHopDong(id);

        return new ResponseEntity<>("Hop dong deleted.", HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<HopDongDto>> getHopDongByUserId(@PathVariable("id") Long userId){
        List<HopDongDto> hopDongs = hopDongService.getHopDongByUserId(userId);
        return ResponseEntity.ok(hopDongs);
    }
    @GetMapping("/canho/{id}")
    public ResponseEntity<List<HopDongDto>> getHopDongByCanHoId(@PathVariable("id") Long canHoId){
        List<HopDongDto> hopDongs = hopDongService.getHopDongByCanHoId(canHoId);
        return ResponseEntity.ok(hopDongs);
    }
    @GetMapping("/dichvu/{id}")
    public ResponseEntity<List<HopDongDto>> getHopDongByDichVuId(@PathVariable("id") Long dichVuId){
        List<HopDongDto> hopDongDtos = hopDongService.getHopDongByDichVuId(dichVuId);
        return ResponseEntity.ok(hopDongDtos);
    }
    @GetMapping
    public ResponseEntity<List<HopDongDto>> getAllHopDong(){
        return new ResponseEntity<>(hopDongService.getAllHopDong(), HttpStatus.OK);
    }
}
