package com.springboot.blog.service.impl;

import com.springboot.blog.entity.DichVu;
import com.springboot.blog.entity.HoaDon;
import com.springboot.blog.entity.HopDong;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.HoaDonDto;
import com.springboot.blog.payload.HopDongDto;
import com.springboot.blog.repository.DichVuRepository;
import com.springboot.blog.repository.HoaDonRepository;
import com.springboot.blog.repository.HopDongRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.HoaDonService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    private HoaDonRepository hoaDonRepository;
    private HopDongRepository hopDongRepository;
    private DichVuRepository dichVuRepository;
    private UserRepository userRepository;

    private ModelMapper modelMapper;
    public HoaDonServiceImpl(HoaDonRepository hoaDonRepository,
                             UserRepository userRepository,HopDongRepository hopDongRepository, ModelMapper modelMapper, DichVuRepository dichVuRepository){
        this.hoaDonRepository = hoaDonRepository;
        this.hopDongRepository = hopDongRepository;
        this.modelMapper = modelMapper;
        this.dichVuRepository = dichVuRepository;
        this.userRepository = userRepository;
    }
    @Override
    public HoaDonDto addHoaDon(HoaDonDto hoaDonDto) {
        HopDong hopDong = hopDongRepository.findById(hoaDonDto.getHopDongId())
                .orElseThrow(() -> new ResourceNotFoundException("Hop dong", "id", hoaDonDto.getHopDongId()));
        HoaDon hoaDon = mapToEntity(hoaDonDto);
        hoaDon.setTongTien(hopDong.getDichVu().getDonGia());
        hoaDon.setHopDong(hopDong);
        if(hoaDon.getNgayThanhToan()== null) hoaDon.setTrangThai("Chưa thanh toán");
        else hoaDon.setTrangThai("Đã thanh toán");
        HoaDon newHoaDon = hoaDonRepository.save(hoaDon);
        return mapToDTO(newHoaDon);
    }
    private HoaDonDto mapToDTO(HoaDon newHoaDon) {
        HoaDonDto hoaDonDto = modelMapper.map(newHoaDon, HoaDonDto.class);
        return  hoaDonDto;
    }

    private HoaDon mapToEntity(HoaDonDto hoaDonDto) {
        HoaDon hoaDon = modelMapper.map(hoaDonDto, HoaDon.class);
        return hoaDon;
    }

    @Override
    public HoaDonDto getHoaDonById(Long id) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hoa don", "id", id));
        return mapToDTO(hoaDon);
    }

    @Override
    public List<HoaDonDto> getHoaDonByHopDongId(Long hopDongId) {
        HopDong hopDong = hopDongRepository.findById(hopDongId)
                .orElseThrow(() -> new ResourceNotFoundException("Can ho", "id", hopDongId));
        List<HoaDon> hoaDons = hoaDonRepository.findHoaDonByHopDongId(hopDongId);
        return hoaDons.stream().map(hoaDon -> mapToDTO(hoaDon))
                .collect(Collectors.toList());
    }

    @Override
    public HoaDonDto updateHoaDon(HoaDonDto hoaDonDto, Long id) {
        HopDong hopDong = hopDongRepository.findById(hoaDonDto.getHopDongId())
                .orElseThrow(() -> new ResourceNotFoundException("Hop dong", "id", hoaDonDto.getHopDongId()));
        HopDongDto hopDongDto = modelMapper.map(hopDong, HopDongDto.class);
        DichVu dichVu = dichVuRepository.findDichVuById(hopDongDto.getDichVuId())
                .orElseThrow(() -> new ResourceNotFoundException("Dich vu","id", hopDongDto.getDichVuId()));
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hoa don", "id", id));

        hoaDon.setNgayLap(hoaDonDto.getNgayLap());
        hoaDon.setNgayThanhToan(hoaDonDto.getNgayThanhToan());
        hoaDon.setTongTien(dichVu.getDonGia());
        hoaDon.setHopDong(hopDong);
        HoaDon updateHoaDon = hoaDonRepository.save(hoaDon);
        return mapToDTO(updateHoaDon);

    }

    @Override
    public void deleteHoaDon(Long id) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hoa don", "id", id));
        hoaDonRepository.delete(hoaDon);

    }

    @Override
    public List<HoaDonDto> getAllHoaDon() {
        List<HoaDon> hoaDons = hoaDonRepository.findAll();
        return hoaDons.stream().map(hoaDon -> mapToDTO(hoaDon)).collect(Collectors.toList());
    }

    @Override
    public List<HoaDonDto> getHoaDonUnpaid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsernameOrEmail(currentPrincipalName, currentPrincipalName).orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND, "không có user"));
        List<HoaDon> hoaDons = hoaDonRepository.findHoaDonByUserIdAndTrangThai(user.getId());
        return hoaDons.stream().map(hoaDon -> mapToDTO(hoaDon)).collect(Collectors.toList());

    }
}
