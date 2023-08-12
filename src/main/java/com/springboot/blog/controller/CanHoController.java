package com.springboot.blog.controller;

import com.springboot.blog.payload.CanHoDto;
import com.springboot.blog.payload.CanHoResponse;
import com.springboot.blog.service.CanHoService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/canho")
public class CanHoController {

    private CanHoService canHoService;

    public CanHoController(CanHoService canHoService){
        this.canHoService = canHoService;
    }
//    @Operation(
//            summary = "Create CanHo REST API",
//            description = "Create Post REST API is used to save post into database"
//    )
//    @ApiResponse(
//            responseCode = "201",
//            description = "Http Status 201 CREATED"
//    )
//    @SecurityRequirement(
//            name = "Bear Authentication"
//    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CanHoDto> addCanHo(@RequestBody CanHoDto canHoDto){
       return new ResponseEntity<>(canHoService.addCanHo(canHoDto), HttpStatus.CREATED);
    }

//    @GetMapping
//    public CanHoResponse getAllCanHo(
//            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
//            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
//            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
//    ){
//        return canHoService.getAllCanHo(pageNo, pageSize, sortBy, sortDir);
//    }
    @GetMapping
    public ResponseEntity<List<CanHoDto>>getAllCanHo(){
        return ResponseEntity.ok(canHoService.getAllCanHo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanHoDto> getCanHoById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(canHoService.getCanHoById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CanHoDto> updateCanHo(@Valid @RequestBody CanHoDto canHoDto, @PathVariable(name = "id") long id){
        CanHoDto canHoResponse = canHoService.updateCanHo(canHoDto, id);
        return new ResponseEntity<>(canHoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCanHo(@PathVariable(name = "id") long id){
        canHoService.deleteCanHo(id);

        return new ResponseEntity<>("Can ho deleted.", HttpStatus.OK);
    }

    @GetMapping("/loaicanho/{id}")
    public ResponseEntity<List<CanHoDto>> getCanHoByLoaiCanHoId(@PathVariable("id") Long loaiCanHoId){
        List<CanHoDto> canHoDtos = canHoService.getCanHoByLoaiCanHoId(loaiCanHoId);
        return ResponseEntity.ok(canHoDtos);
    }

    @GetMapping("/toanha/{id}")
    public ResponseEntity<List<CanHoDto>> getCanHoByToaNhaId(@PathVariable("id") Long toaNhaId){
        List<CanHoDto> canHoDtos = canHoService.getCanHoByToaNhaId(toaNhaId);
        return ResponseEntity.ok(canHoDtos);
    }
}
