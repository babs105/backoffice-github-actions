package sn.sastrans.backofficev2.carburant.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Rajout  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String heure;

    @Positive
    private Double quantityRajout;

//    @ManyToOne
//    @JoinColumn(name="cuveid", insertable=false, updatable=false)
//    private Cuve cuve;
//    private Integer cuveid;
     @ManyToOne
     @JoinColumn(name="cuveid", referencedColumnName = "cuvename",insertable=false, updatable=false)
     private Cuve cuve;
     private String cuveid;
//
//    @ManyToOne
//    @JoinColumn(name="stationid", insertable=false, updatable=false)
//    private Station station;
//    private Integer stationid;

    @ManyToOne
    @JoinColumn(name="stationid", referencedColumnName = "stationname",insertable=false, updatable=false)
    private Station station;
    private String stationid;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    @Temporal(TIMESTAMP)
    private Date deletedAt;

}
