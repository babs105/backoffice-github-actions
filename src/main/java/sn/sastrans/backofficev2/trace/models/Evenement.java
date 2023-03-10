package sn.sastrans.backofficev2.trace.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.security.models.Auditable;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE evenement SET deleted = true,deleted_at = NOW()  WHERE id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Evenement extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Temporal(TemporalType.DATE)
   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEvent;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    @Temporal(TIMESTAMP)
    private Date deletedAt;

    @NotNull
    private String natureEvent;

    @NotNull
    private String causeEvent;

    @NotNull
    private String typeVehicule;

    @NotBlank
    @Size(min = 3)
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
    private LocalDateTime  dateheurePoseBalise;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime  dateheureDeposeBalise;

    private String typeBalisage;
    private String pkDebutBalisage;
    private String pkFinBalisage;
    private String statutEvent;
    private String statutRomEvent;
    private String etatEvent;
    private String observation;

    @OneToMany(mappedBy = "evenement",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Remorquage> remorquages = new ArrayList<>();

    @OneToOne(mappedBy ="evenement",orphanRemoval = true,cascade = CascadeType.ALL)
    private DetailAccident detailAccident;

    private boolean deleted=false;
//
//    @PreRemove
//    public void preRemove() {
////        this.deleted = true;
//        this.deletedBy = SecurityContextHolder.getContext().getAuthentication().getName();
//    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", dateEvent=" + dateEvent +
                ", deletedBy='" + deletedBy + '\'' +
                ", deletedAt=" + deletedAt +
                ", natureEvent='" + natureEvent + '\'' +
                ", causeEvent='" + causeEvent + '\'' +
                ", typeVehicule='" + typeVehicule + '\'' +
                ", matVehicule='" + matVehicule + '\'' +
                ", pkEvent='" + pkEvent + '\'' +
                ", sens='" + sens + '\'' +
                ", voie='" + voie + '\'' +
                ", secteur='" + secteur + '\'' +
                ", localisation='" + localisation + '\'' +
                ", emisPar='" + emisPar + '\'' +
                ", heureDebutEvent='" + heureDebutEvent + '\'' +
                ", heureAppelOPC='" + heureAppelOPC + '\'' +
                ", heureAppelPAT='" + heureAppelPAT + '\'' +
                ", nomPAT='" + nomPAT + '\'' +
                ", heureArriveePAT='" + heureArriveePAT + '\'' +
                ", dateheurePoseBalise=" + dateheurePoseBalise +
                ", dateheureDeposeBalise=" + dateheureDeposeBalise +
                ", typeBalisage='" + typeBalisage + '\'' +
                ", pkDebutBalisage='" + pkDebutBalisage + '\'' +
                ", pkFinBalisage='" + pkFinBalisage + '\'' +
                ", statutEvent='" + statutEvent + '\'' +
                ", statutRomEvent='" + statutRomEvent + '\'' +
                ", etatEvent='" + etatEvent + '\'' +
                ", observation='" + observation + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
