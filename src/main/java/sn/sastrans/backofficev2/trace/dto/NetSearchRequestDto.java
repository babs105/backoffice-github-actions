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
public class NetSearchRequestDto {
//    private Integer id;
    private Date date;
    private String typeNettoyage;
    private LocalDateTime datePose;
    private LocalDateTime dateDepose;
    private String pkdebutBalise;
    private String pkfinBalise;
    private String heureDebut;
    private String heureFin;
    private String pkdebut;
    private String pkfin;
    private String sens;
    private String voie;
    private String zone;
    private String secteur;
    private String gare;
    private String etatNettoyage;
    private int page;
    private int size;
}
