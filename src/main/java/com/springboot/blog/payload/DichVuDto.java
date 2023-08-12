package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DichVuDto {
    private long id;

    @NotNull
    private long donGia;

    @NotNull
    private String tenDichVu;
    private String trangThai;


    @Schema(
            description = "loai dich vu"
    )
    @NotNull
    private long loaiDichVuId;
}
