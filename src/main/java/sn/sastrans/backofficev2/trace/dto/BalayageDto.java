package sn.sastrans.backofficev2.trace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalayageDto {

        private Integer id;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date date;
        private String typeBalayage;
        private String typeBalisage;

        @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
        private LocalDateTime datePose;
        @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
        private LocalDateTime dateDepose;

        private String pkdebutBalise;
        private String pkfinBalise;
        private String heureDebutBalayage;
        private String heureFinBalayage;
        private String pkdebutBalayage;
        private String pkfinBalayage;
        private String lineaire;
        private String autoroute;
        private String sens;
        private String voie;
        private String secteur;

//        @Formula(value = " concat(autoroute,' ',secteur,' ',sens,' ',voie) ")
        private String localisation;
        private String etatBalayage;




}
