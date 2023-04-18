package sn.sastrans.backofficev2.carburant.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.parameters.models.Vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE ravitaillement SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ravitaillement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String heure;

    @PositiveOrZero
    private Integer kilometrage;

    @Positive
    private Double quantity;
    private boolean deleted;
    private String type;


    // @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH) celaque si vehicle est supprimee la relation ne sera pas supprimee

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="matriculeid",referencedColumnName = "matricule",insertable=false,updatable=false)
    private Vehicle vehicle;
    @NotBlank
    private String matriculeid;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "cuveid", referencedColumnName = "cuvename", insertable = false, updatable = false)
    private Cuve cuve;
    private String cuveid;
    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    @Temporal(TIMESTAMP)
    private Date deletedAt;
}
