package sn.sastrans.backofficev2.parameters.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import sn.sastrans.backofficev2.security.models.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE vehicle SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Vehicle extends Auditable<String> implements Serializable{
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
    @Column(name ="matricule",nullable = false,unique = true )
	private String matricule;

//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date registrationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date acquisitionDate;
//	private String description;
    private Double kilometrageActuel;
	private Double kilometrageDVidange;
	private Long cadenceVidange;
	private String statusVidange;
	private String fuelCapacity;
	private boolean deleted;
//	@OneToMany( mappedBy = "vehicle")
//	List<Ravitaillement> ravitaillementList = new ArrayList<>();

//	@PreRemove
//	public void checkRavitaillementAssociationBeforeRemoval() {
//		if (!this.ravitaillementList.isEmpty()) {
//			throw new RuntimeException("Impossible de supprimer Le Vehicule.");
//		}
//	}

//	@ManyToOne
//	@JoinColumn(name="vehiclemakeid", insertable=false, updatable=false)
//	private VehicleMake vehicleMake;
//	private Integer vehiclemakeid;
//
//	@ManyToOne
//	@JoinColumn(name="vehiclemodelid", insertable=false, updatable=false)
//	private VehicleModel vehicleModel;
//	private Integer vehiclemodelid;
//
//	@ManyToOne
//	@JoinColumn(name="vehiclecategoryid", insertable=false, updatable=false)
//	private VehicleCategory vehicleCategory;
//	private Integer vehiclecategoryid;
//
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
	@JoinColumn(name="vehicleaffectationid", referencedColumnName = "title", insertable = false, updatable = false)
	private VehicleAffectation vehicleAffectation;
	private String vehicleaffectationid;
//
//
//	@ManyToOne
//	@JoinColumn(name="vehiclestatusid", insertable=false, updatable=false)
//	private VehicleStatus vehicleStatus;
//	private Integer vehiclestatusid;


	
//	private String netWeight;

//	@ManyToOne
//	@JoinColumn(name="employeeid", insertable=false, updatable=false)
//	private Employee inCharge;
//	private Integer employeeid;


//	@ManyToOne
//	@JoinColumn(name="locationid", insertable=false, updatable=false)
//	private Location currentLocation;
//	private Integer locationid;
	
//	private String remarks;


}
