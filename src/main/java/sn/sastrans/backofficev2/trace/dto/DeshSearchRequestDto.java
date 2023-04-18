package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeshSearchRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    private int page;
    private int size;
}
