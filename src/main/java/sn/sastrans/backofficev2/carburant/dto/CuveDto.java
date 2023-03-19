package sn.sastrans.backofficev2.carburant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuveDto {
    private Integer id;
    @NotBlank
    private String cuveName;
    @PositiveOrZero
    private Double quantity;
    @PositiveOrZero
    private Double indexComptor;
    @PositiveOrZero
    private Double capacity;
}
