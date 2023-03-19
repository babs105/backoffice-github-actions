package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RomSearchExcelRequestDto {

    private Date dateRom;
    private String pka;
    private String secteur;
    private String matVhlRemorque;
    private String catVhlRemorque;
    private String nomROM;
    private String matriculeDep;
    private String lieuDepart;
    private String lieuDepot;
    private String statutRom;

}
