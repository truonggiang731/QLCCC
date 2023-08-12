package com.springboot.blog.controller;

import com.springboot.blog.payload.LoaiDichVuDto;
import com.springboot.blog.payload.ToaNhaDto;
import com.springboot.blog.repository.LoaiDichVuRepository;
import com.springboot.blog.service.LoaiDichVuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loaidichvu")
public class LoaiDichVuController {
    private LoaiDichVuService loaiDichVuService;
    public LoaiDichVuController(LoaiDichVuService loaiDichVuService){
        this.loaiDichVuService = loaiDichVuService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoaiDichVuDto> addLoaiDichVu(@RequestBody LoaiDichVuDto loaiDichVuDto){
        LoaiDichVuDto addLoaiDichVuDto = loaiDichVuService.addLoaiDichVu(loaiDichVuDto);
        return new ResponseEntity<>(addLoaiDichVuDto, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<LoaiDichVuDto> getLoaiDichVu(@PathVariable("id") Long id){
        LoaiDichVuDto loaiDichVuDto = loaiDichVuService.getLoaiDichVuById(id);
        return ResponseEntity.ok(loaiDichVuDto);
    }
    @GetMapping
    public ResponseEntity<List<LoaiDichVuDto>> getAllLoaiDichVu(){
        return ResponseEntity.ok(loaiDichVuService.getAllLoaiDichVu());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<LoaiDichVuDto> updateLoaiDichVu(@RequestBody LoaiDichVuDto loaiDichVuDto,
                                                  @PathVariable("id") Long id){
        return ResponseEntity.ok(loaiDichVuService.updateLoaiDichVu(loaiDichVuDto, id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLoaiDichVu(@PathVariable("id") Long id){
        loaiDichVuService.deleteLoaiDichVu(id);
        return ResponseEntity.ok("Loai Dich Vu deleted successfully!.");
    }
}
