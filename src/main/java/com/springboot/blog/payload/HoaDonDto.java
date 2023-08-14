package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(
        description = "HoaDonDto"
)
public class HoaDonDto {
    private long id;

    @Schema(
            description = "Ngay lap"
    )
    @NotNull
    @Size(min = 2, message = "Ngay lap mustn't be more than current day")
    private LocalDate ngayLap;
    private String trangThai;


    @Schema(
            description = "Ngay thanh toan"
    )

    @Size(min = 2, message = "Ngay thanh toan mustn't be more than current day")
    private LocalDate ngayThanhToan;
    @NotNull
    private Long hopDongId;


    @Schema(
            description = "Tong tien"
    )
    
    private Long tongtien;

}
