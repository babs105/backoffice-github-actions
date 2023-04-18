package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntrusionDto {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date ;
    private String emisPar;
    private String typeIntrusion;
    private String heureAnnonce;
    private String heureDepart;
    private String heureArrivee;
    private String heureFin;
    private String autoroute;
    private String pk;
    private String sens;
    private String voie;
    private String secteur;
    private String localisation;
    private String action;
    private String nbre;
    private String etatIntrusion;
}
