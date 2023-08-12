package com.springboot.blog.service.impl;

import com.springboot.blog.entity.CanHo;
import com.springboot.blog.entity.LoaiCanHo;
import com.springboot.blog.entity.ToaNha;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CanHoDto;
import com.springboot.blog.payload.ToaNhaDto;
import com.springboot.blog.repository.CanHoRepository;
import com.springboot.blog.repository.ToaNhaRepository;
import com.springboot.blog.service.ToaNhaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToaNhaServiceImpl implements ToaNhaService {

    private ToaNhaRepository toaNhaRepository;
    private CanHoRepository canHoRepository;

    private ModelMapper modelMapper;
    @Autowired
    public ToaNhaServiceImpl(ToaNhaRepository toaNhaRepository, ModelMapper modelMapper, CanHoRepository canHoRepository) {
        this.toaNhaRepository = toaNhaRepository;
        this.modelMapper = modelMapper;
        this.canHoRepository = canHoRepository;
    }
    @Override
    public ToaNhaDto addToaNha(ToaNhaDto toaNhaDto) {
        ToaNha toaNha = modelMapper.map(toaNhaDto, ToaNha.class);
        ToaNha saveToaNha = toaNhaRepository.save(toaNha);
        return modelMapper.map(saveToaNha, ToaNhaDto.class);
    }

    @Override
    public ToaNhaDto getToaNha(Long toaNhaId) {
        ToaNha toaNha = toaNhaRepository.findById(toaNhaId)
                .orElseThrow(()-> new ResourceNotFoundException("Toa nha", "id", toaNhaId));

        return modelMapper.map(toaNha, ToaNhaDto.class);
    }

    @Override
    public List<ToaNhaDto> getAllToaNha() {
        List<ToaNha> toaNhas = toaNhaRepository.findAll();
        return toaNhas.stream().map(toaNha -> modelMapper.map(toaNha, ToaNhaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ToaNhaDto updateToaNha(ToaNhaDto toaNhaDto, Long toaNhaId) {
        ToaNha toaNha = toaNhaRepository.findById(toaNhaId)
                .orElseThrow(()-> new ResourceNotFoundException("Toa nha", "id", toaNhaId));
        toaNha.setName(toaNhaDto.getName());
        ToaNha updateToaNha = toaNhaRepository.save(toaNha);
        return modelMapper.map(updateToaNha, ToaNhaDto.class);
    }

    @Override
    public void deleteToaNha(Long toaNhaId) {
        ToaNha toaNha = toaNhaRepository.findById(toaNhaId)
                .orElseThrow(()-> new ResourceNotFoundException("Toa nha", "id", toaNhaId));
        List<CanHo> canHo = canHoRepository.findByToaNhaId(toaNhaId);
        if(canHo == null) toaNhaRepository.delete(toaNha);
        else throw new BlogAPIException(HttpStatus.NOT_ACCEPTABLE,"khong the xoa toa nha");

    }
}
