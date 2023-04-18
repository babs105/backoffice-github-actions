package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
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
        @NotEmpty
        private String natureEvent;
        @NotNull
        private String causeEvent;
        @NotEmpty
        private String typeVehicule;
        @NotEmpty
        private String matVehicule;
        @NotEmpty
        private String pkEvent;
        @NotEmpty
        private String sens;
        @NotEmpty
        private String voie;
        @NotEmpty
        private String secteur;

        @Formula(value = " concat(pk_event, ' ', sens, ' ',voie,' ',secteur) ")
        private String localisation;
        @NotEmpty
        private String emisPar;
        @NotEmpty
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
