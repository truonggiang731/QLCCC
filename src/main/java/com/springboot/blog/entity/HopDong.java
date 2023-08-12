package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hopdong")
public class HopDong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(TemporalType.DATE)
    private Date ngaydangky;
    @Temporal(TemporalType.DATE)
    private Date ngayhethan;
    private String trangThai;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canho_id")
    private CanHo canHo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dichvu_id")
    private DichVu dichVu;
    @OneToMany(mappedBy = "hopDong", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoaDon>  hoaDon;



}
