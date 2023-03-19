package sn.sastrans.backofficev2.carburant.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import sn.sastrans.backofficev2.parameters.models.Location;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE station SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stationname",nullable = false,unique = true)
    private String stationName;

    @ManyToOne
    @JoinColumn(name = "locationid",insertable = false,updatable = false)
    private Location location;
    private Integer locationid;
    private String address;
    private boolean deleted;
    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    @Temporal(TIMESTAMP)
    private Date deletedAt;

}
