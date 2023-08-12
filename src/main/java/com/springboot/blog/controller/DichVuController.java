package com.springboot.blog.controller;

import com.springboot.blog.payload.CanHoDto;
import com.springboot.blog.payload.DichVuDto;
import com.springboot.blog.service.DichVuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/dichvu")
public class DichVuController {
    private DichVuService dichVuService;

    public DichVuController (DichVuService dichVuService){this.dichVuService = dichVuService;}
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DichVuDto> addDichVu(@RequestBody DichVuDto dichVuDto){
        return new ResponseEntity<>(dichVuService.addDichVu(dichVuDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DichVuDto>> getAllDichVu(){
        return ResponseEntity.ok(dichVuService.getAllDichVu());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DichVuDto> getDichVuById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(dichVuService.getDichVuById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DichVuDto> updateDichVu(@Valid @RequestBody DichVuDto dichVuDto, @PathVariable(name = "id") long id){
        DichVuDto dichVuResponse = dichVuService.updateDichVu(dichVuDto, id);
        return new ResponseEntity<>(dichVuResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDichVu(@PathVariable(name = "id") long id){
        dichVuService.deleteDichVu(id);

        return new ResponseEntity<>("Dich Vu deleted.", HttpStatus.OK);
    }

    @GetMapping("/loaidichvu/{id}")
    public ResponseEntity<List<DichVuDto>> getDichVuByLoaiDichVuId(@PathVariable("id") Long loaiDichVuId){
        List<DichVuDto> dichVuDtos = dichVuService.getDichVuByLoaiDichVuId(loaiDichVuId);
        return ResponseEntity.ok(dichVuDtos);
    }


}
