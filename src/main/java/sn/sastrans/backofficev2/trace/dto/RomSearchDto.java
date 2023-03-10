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
public class RomSearchDto {
//    private Integer id;
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
    private int page;
    private int size;
}
