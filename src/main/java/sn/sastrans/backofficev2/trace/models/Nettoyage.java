package sn.sastrans.backofficev2.trace.models;


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
public class Nettoyage extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String typeNettoyage;
//    private String typeBalisage;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime datePose;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateDepose;

    private String pkdebutBalise;
    private String pkfinBalise;
    private String heureDebut;
    private String heureFin;
    private String pkdebut;
    private String pkfin;

    @Formula(value = " concat( sens, ' ',voie,' ',secteur) ")
    private String localisation;

    private String sens;
    private String voie;
    private String zone;
    private String secteur;
    private String gare;
    private String etatNettoyage;

}
