package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcciSearchDto {
    private Date dateAcci;
    private String matriculeVhlImplique;
    private String typeVhlImplique;
    private String causeAccident;
    private String typeAccident;
    private Integer nbreBlesseLeger;
    private Integer nbreBlesseGrave;
    private Integer nbreMort;
    private String pka;
    private String secteur;
    private int page;
    private int size;
}
