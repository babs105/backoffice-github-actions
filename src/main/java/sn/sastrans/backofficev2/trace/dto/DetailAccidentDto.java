package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailAccidentDto {

    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAcci;
    private Integer eventid;
    private String localisation;
    private String matriculeVhlImplique;
    private String typeVhlImplique;
    private String causeAccident;
    private String typeAccident;
    private int nbreBlesseLeger;
    private int nbreBlesseGrave;
    private int nbreMort;
    private String sinistres;
    private String heureArriveeGEN;
    private String heureDepartGEN;
    private String heureArriveePompier;
    private String heureDepartPompier;

}
