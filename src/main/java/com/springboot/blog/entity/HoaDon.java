package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoadon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate ngayLap;
    private LocalDate ngayThanhToan;
    private Long tongTien;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopdong_id")
    private HopDong hopDong;
    private String trangThai;
}
