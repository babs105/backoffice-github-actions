package sn.sastrans.backofficev2.parameters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    private Integer id;
    @Column(name ="matricule",nullable = false,unique = true )
    private String matricule;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acquisitionDate;
    private Double kilometrageActuel;
    private String fuelCapacity;
    private String vehicleaffectationid;
}
