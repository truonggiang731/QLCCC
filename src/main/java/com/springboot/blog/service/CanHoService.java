package com.springboot.blog.service;

import com.springboot.blog.entity.CanHo;
import com.springboot.blog.payload.CanHoDto;
import com.springboot.blog.payload.CanHoResponse;

import java.util.List;

public interface CanHoService {

    //CanHoResponse getAllCanHo(int pageNo, int pageSize, String sortBy, String sortDir);
    List<CanHoDto> getAllCanHo();

    CanHoDto addCanHo(CanHoDto canHoDto);

    CanHoDto getCanHoById(long id);

    void deleteCanHo(long id);

    CanHoDto updateCanHo(CanHoDto canHoDto, long id);

    List<CanHoDto> getCanHoByToaNhaId(Long toaNhaId);

    List<CanHoDto> getCanHoByLoaiCanHoId(Long loaiCanHoId);
}
