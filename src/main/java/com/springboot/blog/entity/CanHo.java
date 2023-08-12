package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "canho")
public class CanHo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String tenCanHo;
    @Column()
    private String ghiChu;
    @Column(nullable = false)
    private String trangThai;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toanha_id")
    private ToaNha toaNha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loaicanho_id")
    private LoaiCanHo loaiCanHo;
    @OneToMany(mappedBy = "canHo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HopDong> hopDong;

}
