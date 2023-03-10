package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.trace.models.DetailAccident;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvenementDto {

        private Integer id;
//    @Temporal(TemporalType.DATE)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date dateEvent;
        @NotNull
        private String natureEvent;
        @NotNull
        private String causeEvent;
        @NotNull
        private String typeVehicule;
        @NotNull
        private String matVehicule;
        @NotNull
        private String pkEvent;
        @NotNull
        private String sens;
        @NotNull
        private String voie;
        @NotNull
        private String secteur;

        @Formula(value = " concat(pk_event, ' ', sens, ' ',voie,' ',secteur) ")
        private String localisation;
        @NotNull
        private String emisPar;
        @NotNull
        private String heureDebutEvent;
        private String heureAppelOPC;
        private String heureAppelPAT;
        private String nomPAT;
        private String heureArriveePAT;


        @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
        private LocalDateTime dateheurePoseBalise;

        @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
        private LocalDateTime  dateheureDeposeBalise;

        private String typeBalisage;
        private String pkDebutBalisage;
        private String pkFinBalisage;
        private String longBalisage;
        private String statutEvent;
        private String statutRomEvent;
        private String etatEvent;
        private String observation;
        private List<RemorquageDto> remorquages = new ArrayList<>();
        private DetailAccidentDto detailAccident;





}
