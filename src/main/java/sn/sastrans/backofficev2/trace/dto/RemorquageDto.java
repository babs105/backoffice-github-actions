package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemorquageDto {

    private  Integer id;
    private Integer eventid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRom;
    private String localisation;
    private String matVhlRemorque;
    private String catVhlRemorque;
    private String heureAppelROM;
    private String nomROM;
    private String matriculeDep;
    private String heureDepartROM;
    private String heureArriveeROM;
    private String heureRomorque;
    private String dureeIntervention;
    private String lieuDepart;
    private String lieuDepot;
    private String statutRom;

}
