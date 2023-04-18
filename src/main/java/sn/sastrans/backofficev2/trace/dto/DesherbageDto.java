package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesherbageDto {
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date ;
    private String typeDesherbage;
    private String heureDebut;
    private String heureFin;
    private String pkDebut;
    private String pkFin;
    private String lineaire;
    private String sens;
    private String secteur;
    private String autoroute;
    private String localisation;
    private String etatDesherbage;
}
