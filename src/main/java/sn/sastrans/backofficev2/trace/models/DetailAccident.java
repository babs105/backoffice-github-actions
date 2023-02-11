package sn.sastrans.backofficev2.trace.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE detail_accident SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class DetailAccident{
//        extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventid",insertable = false,updatable = false)
    private Evenement evenement;
    private  Integer eventid;

    private String matriculeVhlImplique;
    private String typeVhlImplique;
    private String causeAccident;
    private String typeAccident;
    private int nbreBlesseLeger;
    private int nbreBlesseGrave;
    private int nbreMort;
    private String sinistres;
    private String heureArriveeGEN;
    private String heureDepartGEN;
    private String heureArriveePompier;
    private String heureDepartPompier;

    private boolean deleted;

}
//<tr th:each="cycle:${cycleList}">
//<td th:text="${cycle.id}"></td>
//<td th:text="${cycle.nom}"></td>
//<td>
//<div th:each="filList:${cycle.filList}">
//<p th:text="${filList.id}"></p>
//<p th:text="${filList.nom}"></p>
//<p th:text="${filList.dateCreation}"></p>
//</div>
//</td>
//</tr>