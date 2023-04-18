package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeshSearchExcelRequestDto {
//    private Integer id;

    private Date date ;
    private String typeDesherbage;
    private String heureDebut;
    private String heureFin;
    private String pkDebut;
    private String pkFin;
    private String sens;
    private String secteur;
    private String autoroute;
    private String localisation;
    private String etatDesherbage;

}
