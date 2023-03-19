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
public class Location {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	private String name;
//	private String details;
	
	@ManyToOne
	@JoinColumn(name="paysid", insertable=false, updatable=false)
	private Pays pays;
	private Integer paysid;
	
	@ManyToOne
	@JoinColumn(name="regionid", insertable=false, updatable=false)
	private Region region;
	private Integer regionid;
		
	private String city;
	private String address;			
}
