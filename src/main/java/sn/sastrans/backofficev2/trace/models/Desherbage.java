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
public class Desherbage extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date ;
    private String typeDesherbage;
    private String heureDebut;
    private String heureFin;
    @NotNull
    private String pkDebut;
    private String pkFin;
    private String lineaire;
    @NotNull
    private String sens;
    private String autoroute;
    @NotNull
    private String secteur;
    @Formula(value = " concat(autoroute,' ',sens,' ',secteur) ")
    private String localisation;
    private String etatDesherbage;
}
