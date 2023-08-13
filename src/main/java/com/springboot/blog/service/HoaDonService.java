package com.springboot.blog.service;

import com.springboot.blog.payload.HoaDonDto;

import java.util.List;

public interface HoaDonService {
    HoaDonDto addHoaDon(HoaDonDto hoaDonDto);
    HoaDonDto getHoaDonById(Long id);
    List<HoaDonDto> getHoaDonByHopDongId(Long hopDongId);
    HoaDonDto updateHoaDon(HoaDonDto hoaDonDto, Long id);
    void deleteHoaDon(Long id);
    List<HoaDonDto> getAllHoaDon();
}
