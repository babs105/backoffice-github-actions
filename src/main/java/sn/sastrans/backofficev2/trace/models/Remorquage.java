package sn.sastrans.backofficev2.trace.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.security.models.Auditable;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE remorquage SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Remorquage extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @ManyToOne
    @JoinColumn(name ="eventid" ,insertable = false ,updatable = false )
    private Evenement evenement;
    private Integer eventid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRom;

    private String matVhlRemorque;
    private String catVhlRemorque;
    private String heureAppelROM;
    private String nomROM;
    private String matriculeDep;
    private String heureDepartROM;
    private String heureArriveeROM;
    private String heureRomorque;
    private String lieuDepart;
    private String lieuDepot;
    private String statutRom;

    private boolean deleted;
    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    @Temporal(TIMESTAMP)
    private Date deletedAt;
}
