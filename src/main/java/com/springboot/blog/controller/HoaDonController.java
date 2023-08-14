package com.springboot.blog.controller;

import com.springboot.blog.entity.HoaDon;
import com.springboot.blog.payload.HoaDonDto;
import com.springboot.blog.payload.HopDongDto;
import com.springboot.blog.service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hoadon")
public class HoaDonController {
    private HoaDonService hoaDonService;
    public HoaDonController(HoaDonService hoaDonService){
        this.hoaDonService = hoaDonService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HoaDonDto> addHoaDon(@RequestBody HoaDonDto hoaDonDto){
        return new ResponseEntity<>(hoaDonService.addHoaDon(hoaDonDto), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HoaDonDto> getHoaDonById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(hoaDonService.getHoaDonById(id));
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HoaDonDto> updateHoaDon(@Valid @RequestBody HoaDonDto hoaDonDto, @PathVariable(name = "id") long id){
        HoaDonDto hoaDonResponse = hoaDonService.updateHoaDon(hoaDonDto, id);
        return new ResponseEntity<>(hoaDonResponse, HttpStatus.OK);
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHoaDon(@PathVariable(name = "id") long id){
        hoaDonService.deleteHoaDon(id);

        return new ResponseEntity<>("Hoa don deleted.", HttpStatus.OK);
    }
    @GetMapping("/hopdong/{id}")
    public ResponseEntity<List<HoaDonDto>> getHoaDonByHopDongId(@PathVariable("id") Long hopDongId){
        List<HoaDonDto> hoaDonDtos = hoaDonService.getHoaDonByHopDongId(hopDongId);
        return ResponseEntity.ok(hoaDonDtos);
    }
    @GetMapping
    public ResponseEntity<List<HoaDonDto>> getAllHoaDon(){
        return new ResponseEntity<>(hoaDonService.getAllHoaDon(), HttpStatus.OK);
    }
    @GetMapping("/unpaid")
    public ResponseEntity<List<HoaDonDto>> getAllHoaDonUnpaid(){
        return new ResponseEntity<>(hoaDonService.getHoaDonUnpaid(), HttpStatus.OK);
    }

}
