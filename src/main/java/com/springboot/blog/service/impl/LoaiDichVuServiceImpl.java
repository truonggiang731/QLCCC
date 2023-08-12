package com.springboot.blog.service.impl;

import com.springboot.blog.entity.DichVu;
import com.springboot.blog.entity.LoaiDichVu;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.LoaiDichVuDto;
import com.springboot.blog.repository.LoaiDichVuRepository;
import com.springboot.blog.service.LoaiDichVuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoaiDichVuServiceImpl implements LoaiDichVuService {
    private ModelMapper modelMapper;
    private LoaiDichVuRepository loaiDichVuRepository;
    @Autowired
    public LoaiDichVuServiceImpl(LoaiDichVuRepository loaiDichVuRepository, ModelMapper modelMapper){
        this.loaiDichVuRepository = loaiDichVuRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public LoaiDichVuDto addLoaiDichVu(LoaiDichVuDto loaiDichVuDto){
        if(loaiDichVuDto != null) System.out.println(loaiDichVuDto.getName());
        else System.out.println("0000000000000000000000000");

        LoaiDichVu loaiDichVu = modelMapper.map(loaiDichVuDto, LoaiDichVu.class);
        if(loaiDichVu != null) System.out.println(loaiDichVu.getName());
        else System.out.println("0000000000000000000000000");
        LoaiDichVu saveLoaiDichVu = loaiDichVuRepository.save(loaiDichVu);
        return modelMapper.map(saveLoaiDichVu,LoaiDichVuDto.class);
    }

    @Override
    public LoaiDichVuDto getLoaiDichVuById(Long id) {
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loai dich vu", "id", id));
        return modelMapper.map(loaiDichVu,LoaiDichVuDto.class);

    }

    @Override
    public LoaiDichVuDto updateLoaiDichVu(LoaiDichVuDto loaiDichVuDto, Long id) {
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loai dich vu", "id", id));

        loaiDichVu.setName(loaiDichVuDto.getName());
        LoaiDichVu updateLoaiDichVu = loaiDichVuRepository.save(loaiDichVu);
        return modelMapper.map(updateLoaiDichVu, LoaiDichVuDto.class);
    }

    @Override
    public List<LoaiDichVuDto> getAllLoaiDichVu() {
        List<LoaiDichVu> loaiDichVus = loaiDichVuRepository.findAll();
        return loaiDichVus.stream().map(loaiDichVu -> modelMapper.map(loaiDichVu, LoaiDichVuDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLoaiDichVu(Long id) {
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loai dich vu", "id", id));
        loaiDichVuRepository.delete(loaiDichVu);
    }



}
