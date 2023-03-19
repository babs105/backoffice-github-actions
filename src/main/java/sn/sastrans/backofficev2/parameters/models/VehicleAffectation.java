package sn.sastrans.backofficev2.parameters.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@SQLDelete(sql = "UPDATE vehicle_affectation SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VehicleAffectation extends CommonObject implements Serializable  {


    @OneToMany(mappedBy ="vehicleAffectation")
    Set<Vehicle> vehicles =new HashSet<>();
    private boolean deleted;
}
