package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventSearchRequestDto {
    //    private Integer id;
    private Date dateEvent;
    private String natureEvent;
    private String causeEvent;
    private String typeVehicule;
    private String matVehicule;
    private String pkEvent;
    private String sens;
    private String voie;
    private String secteur;
    private String emisPar;
    private String statutEvent;
    private String statutRomEvent;
    private String etatEvent;
    private Integer page;
    private Integer size;
}
