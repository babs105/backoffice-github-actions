package sn.sastrans.backofficev2.security.models;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE role SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Role extends Auditable<String>  {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();
    private boolean deleted;
}