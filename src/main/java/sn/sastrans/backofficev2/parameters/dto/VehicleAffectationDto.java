package sn.sastrans.backofficev2.parameters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleAffectationDto {
    private Integer id;
    private String title;
    private String details;
}
