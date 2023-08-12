package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Data
@Schema(
        description = "CanHoDto"
)
public class CanHoDto {
    private long id;

    @Schema(
            description = "Ten can ho"
    )
    @NotEmpty
    @Size(min = 2, message = "Ten can ho should have at least 2 characters")
    private String tenCanHo;

    private String ghiChu;
    private String trangThai;

//    @Schema(
//            description = "Chu can ho"
//    )
//    @NotEmpty
//    private Long userid;

    @Schema(
            description = "Loai can ho"
    )
    @NotNull
    private Long loaiCanHoId;

    @Schema(
            description = "Toa nha chua can ho"
    )
    @NotNull
    private Long toaNhaId;
}

