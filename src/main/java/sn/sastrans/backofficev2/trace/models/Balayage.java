package sn.sastrans.backofficev2.trace.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.security.models.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balayage  extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String typeBalayage;
    private String typeBalisage;
//
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

    private String autoroute;
    private String sens;
    private String voie;
    private String secteur;
//    @Formula(value = " concat(pk_event, ' ', sens, ' ',voie,' ',secteur) ")
    @Formula(value = " concat(autoroute,' ',secteur,' ',sens,' ',voie) ")
    private String localisation;
    private String etatBalayage;

}
