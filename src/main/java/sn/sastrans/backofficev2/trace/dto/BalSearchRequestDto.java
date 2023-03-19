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
public class BalSearchRequestDto {
//    private Integer id;
     private Date date;
    private String typeBalayage;
    private String typeBalisage;
//    private LocalDateTime datePose;
//    private LocalDateTime dateDepose;
    private String pkdebutBalise;
    private String pkfinBalise;
    private String pkdebutBalayage;
    private String pkfinBalayage;
    private String autoroute;
    private String sens;
    private String voie;
    private String secteur;
    private String etatBalayage;
    private int page;
    private int size;
}
