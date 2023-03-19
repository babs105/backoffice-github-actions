package sn.sastrans.backofficev2.parameters.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VehicleModel extends CommonObject {
  @ManyToOne
  @JoinColumn(name ="vehiclemakeid",insertable = false,updatable = false )
  private VehicleMake vehicleMake;
  private Integer vehiclemakeid;


}
