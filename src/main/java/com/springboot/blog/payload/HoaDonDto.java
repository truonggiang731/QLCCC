package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        description = "HoaDonDto"
)
public class HoaDonDto {
    private long id;

    @Schema(
            description = "Ngay lap"
    )
    @NotEmpty
    @Size(min = 2, message = "Ngay lap mustn't be more than current day")
    private String ngayLap;
    private String trangThai;


    @Schema(
            description = "Ngay thanh toan"
    )
    @NotEmpty
    @Size(min = 2, message = "Ngay thanh toan mustn't be more than current day")
    private String ngayThanhToan;
    private Long hopDongId;

//    @Schema(
//            description = "chi tiet hoa don"
//    )
//    @NotEmpty
//    private List<ChiTietHoaDon> chiTietHoaDonList;

//    @Schema(
//            description = "Can ho"
//    )
//    @NotEmpty
//    private Long canHoId;

    @Schema(
            description = "Tong tien"
    )
    @NotEmpty
    private Long tongtien;
}
