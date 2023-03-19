package sn.sastrans.backofficev2.parameters.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class VehicleCategory extends CommonObject {
}
