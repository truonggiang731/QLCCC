package com.springboot.blog.controller;

import com.springboot.blog.payload.LoaiCanHoDto;
import com.springboot.blog.service.LoaiCanHoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loaicanho")
public class LoaiCanHoController {
    private LoaiCanHoService loaiCanHoService;

    public LoaiCanHoController(LoaiCanHoService loaiCanHoService){
        this.loaiCanHoService = loaiCanHoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoaiCanHoDto> addLoaiCanHo(@RequestBody LoaiCanHoDto loaiCanHoDto){
        LoaiCanHoDto saveLoaiCanHo = loaiCanHoService.addLoaiCanHo(loaiCanHoDto);
        return new ResponseEntity<>(saveLoaiCanHo, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<LoaiCanHoDto> getLoaiCanHo(@PathVariable("id") Long loaiCanHoId){
        LoaiCanHoDto loaiCanHoDto = loaiCanHoService.getLoaiCanHo(loaiCanHoId);
        return ResponseEntity.ok(loaiCanHoDto);
    }

    // Build Get All Categories REST API
    @GetMapping
    public ResponseEntity<List<LoaiCanHoDto>> getAllLoaiCanHo(){
        return ResponseEntity.ok(loaiCanHoService.getAllLoaiCanHo());
    }

    // Build Update Category REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<LoaiCanHoDto> updateLoaiCanHo(@RequestBody LoaiCanHoDto loaiCanHoDto,
                                                      @PathVariable("id") Long loaiCanHoId){
        return ResponseEntity.ok(loaiCanHoService.updateLoaiCanHo(loaiCanHoDto, loaiCanHoId));
    }

    // Build Delete Category REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLoaiCanHo(@PathVariable("id") Long loaiCanHoId){
        loaiCanHoService.deleteLoaiCanHo(loaiCanHoId);
        return ResponseEntity.ok("Loai Can ho deleted successfully!.");
    }
}
