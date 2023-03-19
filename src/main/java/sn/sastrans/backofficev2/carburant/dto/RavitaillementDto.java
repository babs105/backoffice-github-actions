package sn.sastrans.backofficev2.carburant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.parameters.models.Vehicle;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RavitaillementDto {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String heure;
    @PositiveOrZero
    private Integer kilometrage;
    @Positive
    private Double quantity;
    private String type;

   private  String matriculeid;
    private String cuveid;
    private String vehicleaffectationid;
}
