package sn.sastrans.backofficev2.parameters.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Fournisseur {
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	private String name;
	private String address;
	private String city;
	private String phone;
	private String mobile;
	private String website;
	private String email;
	
	@ManyToOne
	@JoinColumn(name="paysid", insertable=false, updatable=false)
	private Pays pays;
	private Integer paysid;
	
	@ManyToOne
	@JoinColumn(name="regionid", insertable=false, updatable=false)
	private Region region;
	private Integer regionid;
	
	private String details;
}
