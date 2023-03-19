package sn.sastrans.backofficev2.carburant.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.context.SecurityContextHolder;
import sn.sastrans.backofficev2.security.models.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE cuve SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cuve extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 5)
    @Column(name ="cuvename",nullable = false,unique = true )
    private String cuveName;

    @PositiveOrZero
    private Double quantity;

    @PositiveOrZero
    private Double indexComptor;

    @PositiveOrZero
    private Double capacity;
    private boolean deleted;
    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    @Temporal(TIMESTAMP)
    private Date deletedAt;

    @PreRemove
    public void preRemove() {
//        this.deleted = true;
        this.deletedBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @PostRemove
    public void postRemove() {
//        this.deleted = true;
        this.deletedAt = new Date();
    }
}
