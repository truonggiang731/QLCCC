package com.springboot.blog.service.impl;

import com.springboot.blog.entity.*;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CanHoDto;
import com.springboot.blog.payload.CanHoResponse;
import com.springboot.blog.repository.*;
import com.springboot.blog.service.CanHoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CanHoServiceImpl implements CanHoService {

    private CanHoRepository canHoRepository;

    private ModelMapper modelMapper;

    private LoaiCanHoRepository loaiCanHoRepository;

    private ToaNhaRepository toaNhaRepository;

    private UserRepository userRepository;
    private HopDongRepository hopDongRepository;


    public CanHoServiceImpl(CanHoRepository canHoRepository,HopDongRepository hopDongRepository,
                            ModelMapper modelMapper, LoaiCanHoRepository loaiCanHoRepository, ToaNhaRepository toaNhaRepository, UserRepository userRepository){
        this.canHoRepository = canHoRepository;
        this.modelMapper = modelMapper;
        this.loaiCanHoRepository = loaiCanHoRepository;
        this.toaNhaRepository = toaNhaRepository;
        this.userRepository = userRepository;
        this.hopDongRepository = hopDongRepository;
    }

//    @Override
//    public CanHoResponse getAllCanHo(int pageNo, int pageSize, String sortBy, String sortDir){
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        // create Pageable instance
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//
//        Page<CanHo> canHos = canHoRepository.findAll(pageable);
//
//        // get content for page object
//        List<CanHo> listOfCanHo = canHos.getContent();
//
//        List<CanHoDto> content= listOfCanHo.stream().map(canHo -> mapToDTO( canHo)).collect(Collectors.toList());
//
//        CanHoResponse canHoReponse = new CanHoResponse();
//        canHoReponse.setContent(content);
//        canHoReponse.setPageNo(canHos.getNumber());
//        canHoReponse.setPageSize(canHos.getSize());
//        canHoReponse.setTotalElements(canHos.getTotalElements());
//        canHoReponse.setTotalPages(canHos.getTotalPages());
//        canHoReponse.setLast(canHos.isLast());
//
//        return canHoReponse;
//    }
    @Override
    public List<CanHoDto> getAllCanHo(){
        List<CanHo> canHos = canHoRepository.findAll();
        return canHos.stream().map(canHo -> modelMapper.map(canHo, CanHoDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public CanHoDto addCanHo(CanHoDto canHoDto) {
        LoaiCanHo loaiCanHo = loaiCanHoRepository.findById(canHoDto.getLoaiCanHoId())
                .orElseThrow(() -> new ResourceNotFoundException("Loai Can Ho","id", canHoDto.getLoaiCanHoId()));
        ToaNha toaNha = toaNhaRepository.findById((canHoDto.getToaNhaId()))
                .orElseThrow(()-> new ResourceNotFoundException("Toa Nha","id",canHoDto.getToaNhaId()));

        CanHo canHo = mapToEntity(canHoDto);
        canHo.setLoaiCanHo(loaiCanHo);
        canHo.setToaNha(toaNha);
        canHo.setTrangThai("Chưa được sử dụng");
        CanHo newCanHo = canHoRepository.save(canHo);

        CanHoDto canHoResponse = mapToDTO(newCanHo);
        return canHoResponse;
    }



    private CanHoDto mapToDTO(CanHo newCanHo) {
        CanHoDto canHoDto = modelMapper.map(newCanHo, CanHoDto.class);
        return  canHoDto;
    }

    private CanHo mapToEntity(CanHoDto canHoDto) {
        CanHo canHo = modelMapper.map(canHoDto, CanHo.class);
        return canHo;
    }

    @Override
    public CanHoDto getCanHoById(long id) {
        CanHo canHo = canHoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can Ho", "id", id));
        return mapToDTO(canHo);
    }

    @Override
    public void deleteCanHo(long id) {
        CanHo canHo = canHoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can Ho", "id", id));
        List<HopDong> hopDong = hopDongRepository.findHopDongByCanHoId(id);
        if(hopDong == null) canHoRepository.delete(canHo);
        else throw new BlogAPIException(HttpStatus.NOT_ACCEPTABLE, "can ho khong the xoa");
    }

    @Override
    public CanHoDto updateCanHo(CanHoDto canHoDto, long id) {
        CanHo canHo = canHoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can Ho", "id", id));

        LoaiCanHo loaiCanHo = loaiCanHoRepository.findById(canHoDto.getLoaiCanHoId())
                .orElseThrow(() -> new ResourceNotFoundException("Loai Can Ho", "id", canHoDto.getLoaiCanHoId()));
        ToaNha toaNha = toaNhaRepository.findById(canHoDto.getToaNhaId())
                .orElseThrow(() -> new ResourceNotFoundException("Toa Nha", "id", canHoDto.getToaNhaId()));


        canHo.setTenCanHo(canHoDto.getTenCanHo());
        canHo.setGhiChu(canHoDto.getGhiChu());
        canHo.setLoaiCanHo(loaiCanHo);
        canHo.setToaNha(toaNha);
        CanHo updatedCanHo = canHoRepository.save(canHo);
        return mapToDTO(updatedCanHo);
    }

    @Override
    public List<CanHoDto> getCanHoByToaNhaId(Long toaNhaId) {
        ToaNha toaNha = toaNhaRepository.findById(toaNhaId)
                .orElseThrow(() -> new ResourceNotFoundException("Toa Nha", "id", toaNhaId));

        List<CanHo> canHos = canHoRepository.findByToaNhaId(toaNhaId);

        return canHos.stream().map(canHo -> mapToDTO(canHo))
                .collect(Collectors.toList());
    }

    @Override
    public List<CanHoDto> getCanHoByLoaiCanHoId(Long loaiCanHoId) {

        LoaiCanHo loaiCanHo = loaiCanHoRepository.findById(loaiCanHoId)
                .orElseThrow(() -> new ResourceNotFoundException("Loai Can Ho", "id", loaiCanHoId));

        List<CanHo> canHos = canHoRepository.findByLoaiCanHoId(loaiCanHoId);

        return canHos.stream().map(canHo -> mapToDTO(canHo))
                .collect(Collectors.toList());
    }
}
