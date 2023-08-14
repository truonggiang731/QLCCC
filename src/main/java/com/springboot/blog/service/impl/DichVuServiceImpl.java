package com.springboot.blog.service.impl;

import com.springboot.blog.entity.DichVu;
import com.springboot.blog.entity.HopDong;
import com.springboot.blog.entity.LoaiDichVu;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.DichVuDto;
import com.springboot.blog.repository.DichVuRepository;
import com.springboot.blog.repository.HopDongRepository;
import com.springboot.blog.repository.LoaiDichVuRepository;
import com.springboot.blog.service.DichVuService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DichVuServiceImpl implements DichVuService {
    private DichVuRepository dichVuRepository;
    private LoaiDichVuRepository loaiDichVuRepository;
    private HopDongRepository hopDongRepository;
    private ModelMapper modelMapper;
    public DichVuServiceImpl(DichVuRepository dichVuRepository, ModelMapper modelMapper,
                             LoaiDichVuRepository loaiDichVuRepository,
                             HopDongRepository hopDongRepository){
        this.dichVuRepository =dichVuRepository;
        this.hopDongRepository = hopDongRepository;
        this.loaiDichVuRepository = loaiDichVuRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public DichVuDto addDichVu(DichVuDto dichVuDto) {
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(dichVuDto.getLoaiDichVuId())
                .orElseThrow(()->new ResourceNotFoundException("Loai dich vu","id",dichVuDto.getLoaiDichVuId()));

        DichVu dichVu = mapToEntity(dichVuDto);
        dichVu.setLoaiDichVu(loaiDichVu);
        dichVu.setTrangThai("Chưa sử dụng");
        DichVu newDichVu = dichVuRepository.save(dichVu);

        return mapToDTO(newDichVu);

    }
    private DichVuDto mapToDTO(DichVu newDichVu) {
        DichVuDto dichVuDto = modelMapper.map(newDichVu, DichVuDto.class);
        return  dichVuDto;
    }

    private DichVu mapToEntity(DichVuDto dichVuDto) {
        DichVu dichVu = modelMapper.map(dichVuDto, DichVu.class);
        return dichVu;
    }

    @Override
    public DichVuDto getDichVuById(Long id) {
        DichVu dichVu = dichVuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dich vu", "id", id));
        return  mapToDTO(dichVu);
    }

    @Override
    public List<DichVuDto> getDichVuByLoaiDichVuId(Long loaiDichVuId) {
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(loaiDichVuId)
                .orElseThrow(()->new ResourceNotFoundException("Loai dich vu","id",loaiDichVuId));

        List<DichVu> dichVus = dichVuRepository.findDichVuByLoaiDichVu(loaiDichVuId);

        return  dichVus.stream().map(dichVu -> mapToDTO(dichVu))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDichVu(Long id) {
        DichVu dichVu = dichVuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dich vu", "id", id));
        List<HopDong> hopDongs = hopDongRepository.findHopDongByDichVuId(id);
        if(hopDongs.isEmpty())
        dichVuRepository.delete(dichVu);
        else {
            throw new BlogAPIException(HttpStatus.NOT_ACCEPTABLE, "Không thể xóa dịch vụ đã có hợp đồng");
        }
    }

    @Override
    public DichVuDto updateDichVu(DichVuDto dichVuDto, long id) {
        DichVu dichVu = dichVuRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Dich vu","id",id));
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(dichVuDto.getLoaiDichVuId())
                .orElseThrow(()->new ResourceNotFoundException("Loai dich vu","id",dichVuDto.getLoaiDichVuId()));

        dichVu.setTenDichVu(dichVuDto.getTenDichVu());
        dichVu.setDonGia(dichVuDto.getDonGia());
        dichVu.setLoaiDichVu(loaiDichVu);
        DichVu updateDichVu = dichVuRepository.save(dichVu);
        return mapToDTO(updateDichVu);
    }

    @Override
    public List<DichVuDto> getAllDichVu() {
        List<DichVu> dichVus = dichVuRepository.findAll();
        return dichVus.stream().map(dichVu -> mapToDTO(dichVu))
                .collect(Collectors.toList());
    }
}
