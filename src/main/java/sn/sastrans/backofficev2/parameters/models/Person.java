package sn.sastrans.backofficev2.parameters.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Person {
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String firstname;
	private String lastname;
	private String othername;

	@Formula(value = " concat(firstname, ' ', lastname) ")
	private String fullName;

	private String title;
	private String initials;
	private String socialSecurityNumber;
	private String gender;
	private String maritalStatus;

//	@ManyToOne
//	@JoinColumn(name="paysid", insertable=false, updatable=false)
//	private Pays pays;
//	private Integer paysid;
	private String paysid;

//	@ManyToOne
//	@JoinColumn(name="regionid", insertable=false, updatable=false)
//	private Region region;
//	private Integer regionid;

	private String regionid;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	private String city;
	private String address;
	private String phone;
	private String mobile;
	private String email;
	private byte [] photo;
}
