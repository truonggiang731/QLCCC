package com.springboot.blog.service;

import com.springboot.blog.entity.HopDong;
import com.springboot.blog.payload.HopDongDto;

import java.util.List;

public interface HopDongService {

    HopDongDto addHopDong(HopDongDto hopDongDto);
    HopDongDto addHopDongForAdmin(HopDongDto hopDongDto);
    HopDongDto updateHopDong(HopDongDto hopDongDto, long id);
    HopDongDto getHopDongById(long id);
    void deleteHopDong(long id);
    List<HopDongDto> getHopDongByCanHoId(Long canHoId);
    List<HopDongDto> getHopDongByUserId();
    List<HopDongDto> getHopDongByDichVuId(Long dichVuid);
    List<HopDongDto> getAllHopDong();

}
