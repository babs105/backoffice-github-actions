package sn.sastrans.backofficev2.trace.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import sn.sastrans.backofficev2.trace.dto.EventSearchDTO;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.repositories.EvenementRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class EvenementRepositoryImpl implements EvenementRepositoryCustom {

    @Autowired
   private  EntityManager entityManager;

    @Override
    public List<Evenement> searchEvenement(EventSearchDTO critere) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evenement> cquery = cbuild.createQuery(Evenement.class);
        Root<Evenement> evenementRoot = cquery.from(Evenement.class);

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getMatricule()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("matVehicule"),critere.getMatricule()));
        }
        if(critere.getPka()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("pkEvent"),critere.getPka()));
        }

        cquery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cquery).getResultList();
    }
}
