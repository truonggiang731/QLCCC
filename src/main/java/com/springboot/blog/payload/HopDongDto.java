package com.springboot.blog.payload;

import com.springboot.blog.entity.CanHo;
import com.springboot.blog.entity.DichVu;
import com.springboot.blog.entity.HoaDon;
import com.springboot.blog.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Date;

@Data
public class HopDongDto {
    private long id;
    private String ngaydangky;
    private String ngayhethan;
    private String trangThai;

    private long userId;

    private long canHoId;

    private long dichVuId;

}
