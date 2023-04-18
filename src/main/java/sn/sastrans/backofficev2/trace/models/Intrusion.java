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
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intrusion extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date ;
    private String emisPar;
    private String typeIntrusion;
    private String heureAnnonce;
    private String heureDepart;
    private String heureArrivee;
    private String heureFin;
    @NotNull
    private String pk;
    @NotNull
    private String sens;
    @NotNull
    private String voie;
    @NotNull
    private String secteur;
    private String autoroute;
    @Formula(value = " concat(autoroute,' ', pk, ' ', sens, ' ',voie,' ',secteur) ")
    private String localisation;

    private String action;
    private String nbre;
    private String etatIntrusion;
}
