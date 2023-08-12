package com.springboot.blog.service.impl;

import com.springboot.blog.entity.CanHo;
import com.springboot.blog.entity.DichVu;
import com.springboot.blog.entity.HopDong;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.HopDongDto;
import com.springboot.blog.repository.CanHoRepository;
import com.springboot.blog.repository.DichVuRepository;
import com.springboot.blog.repository.HopDongRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.HopDongService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class HopDongServiceImpl implements HopDongService {
    private HopDongRepository hopDongRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CanHoRepository canHoRepository;
    private DichVuRepository dichVuRepository;
    public HopDongServiceImpl(HopDongRepository hopDongRepository, ModelMapper modelMapper,DichVuRepository dichVuRepository, UserRepository userRepository, CanHoRepository canHoRepository){
        this.hopDongRepository = hopDongRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.canHoRepository = canHoRepository;
        this.dichVuRepository = dichVuRepository;
    }
    @Override
    public HopDongDto addHopDong(HopDongDto hopDongDto) {
        User user = userRepository.findById(hopDongDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", hopDongDto.getUserId()));
        CanHo canHo = canHoRepository.findById(hopDongDto.getCanHoId())
                .orElseThrow(()->new ResourceNotFoundException("can ho","id",hopDongDto.getCanHoId()));
        DichVu dichVu = dichVuRepository.findById(hopDongDto.getDichVuId())
                .orElseThrow(()->new ResourceNotFoundException("dich vu","id",hopDongDto.getDichVuId()));

        HopDong hopDong = mapToEntity(hopDongDto);
        hopDong.setUser(user);
        hopDong.setCanHo(canHo);
        hopDong.setDichVu(dichVu);
        HopDong newHopDong = hopDongRepository.save(hopDong);

        return mapToDTO(newHopDong);

    }

    private HopDongDto mapToDTO(HopDong newHopDong) {
        HopDongDto hopDongDto = modelMapper.map(newHopDong, HopDongDto.class);
        return  hopDongDto;
    }

    private HopDong mapToEntity(HopDongDto hopDongDto) {
        HopDong hopDong = modelMapper.map(hopDongDto, HopDong.class);
        return hopDong;
    }

    @Override
    public HopDongDto updateHopDong(HopDongDto hopDongDto, long id) {
        HopDong hopDong = hopDongRepository.findById(hopDongDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hop Dong", "id", id));

        User user = userRepository.findById(hopDongDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", hopDongDto.getUserId()));
        CanHo canHo = canHoRepository.findById(hopDongDto.getCanHoId())
                .orElseThrow(()->new ResourceNotFoundException("can ho","id",hopDongDto.getCanHoId()));
        DichVu dichVu = dichVuRepository.findById(hopDongDto.getDichVuId())
                .orElseThrow(()->new ResourceNotFoundException("dich vu","id",hopDongDto.getDichVuId()));

        hopDong.setNgaydangky(hopDongDto.getNgaydangky());
        hopDong.setNgayhethan(hopDongDto.getNgayhethan());
        hopDong.setUser(user);
        hopDong.setCanHo(canHo);
        hopDong.setDichVu(dichVu);
        HopDong updateHopDong = hopDongRepository.save(hopDong);
        return mapToDTO(updateHopDong);

    }

    @Override
    public HopDongDto getHopDongById(long id) {
        HopDong hopDong = hopDongRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hop dong", "id", id));
        return  mapToDTO(hopDong);
    }

    @Override
    public void deleteHopDong(long id) {
        HopDong hopDong = hopDongRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hop dong", "id", id));
        hopDong.setTrangThai("Đã hủy");
        hopDongRepository.save(hopDong);
    }

    @Override
    public List<HopDongDto> getHopDongByCanHoId(Long canHoId) {
        CanHo canHo = canHoRepository.findById(canHoId)
                .orElseThrow(()->new ResourceNotFoundException("can ho","id",canHoId));
        List<HopDong> hopDongs = hopDongRepository.findHopDongByCanHoId(canHoId);

        return hopDongs.stream().map(hopDong -> mapToDTO(hopDong))
                .collect(Collectors.toList());

    }

    @Override
    public List<HopDongDto> getHopDongByUserId(Long userid) {
        User user = userRepository.findById(userid)
                .orElseThrow(()->new ResourceNotFoundException("user","id",userid));
        List<HopDong> hopDongs = hopDongRepository.findHopDongByUserId(userid);

        return hopDongs.stream().map(hopDong -> mapToDTO(hopDong))
                .collect(Collectors.toList());
    }

    @Override
    public List<HopDongDto> getHopDongByDichVuId(Long dichVuId) {
        DichVu dichVu = dichVuRepository.findById(dichVuId)
                .orElseThrow(()->new ResourceNotFoundException("Dich vu","id",dichVuId));
        List<HopDong> hopDongs = hopDongRepository.findHopDongByDichVuId(dichVuId);

        return hopDongs.stream().map(hopDong -> mapToDTO(hopDong))
                .collect(Collectors.toList());
    }

    @Override
    public List<HopDongDto> getAllHopDong() {
        List<HopDong> hopDongs = hopDongRepository.findAll();
        return hopDongs.stream().map(hopDong -> mapToDTO(hopDong)).collect(Collectors.toList());
    }
}
