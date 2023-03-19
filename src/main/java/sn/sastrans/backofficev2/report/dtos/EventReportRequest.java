package sn.sastrans.backofficev2.report.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventReportRequest {
    private Date dateDebut;
    private Date dateFin;
    private String secteur;
    private int page;
    private int size;

}
