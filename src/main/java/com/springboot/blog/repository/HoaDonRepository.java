package com.springboot.blog.repository;

import com.springboot.blog.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    List<HoaDon> findHoaDonByHopDongId(long hopDongId);
    @Query(value = "SELECT hd.* FROM hoadon hd JOIN hopdong h ON hd.hopdong_id = h.id WHERE h.user_id = :userId AND hd.trang_thai = 'Chưa thanh toán';", nativeQuery = true)
    List<HoaDon> findHoaDonByUserIdAndTrangThai(@Param("userId") Long userId);


}
