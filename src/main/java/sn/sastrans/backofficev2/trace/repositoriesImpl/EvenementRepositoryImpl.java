package sn.sastrans.backofficev2.trace.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import sn.sastrans.backofficev2.trace.dto.EventSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;
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
    public Page<Evenement> searchEvenement(EventSearchRequestDto critere,Pageable paging) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evenement> cquery = cbuild.createQuery(Evenement.class);
        Root<Evenement> evenementRoot = cquery.from(Evenement.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getMatVehicule()!=null){
            predicates.add(cbuild.like(evenementRoot.get("matVehicule"),"%" + critere.getMatVehicule()+"%" )); //root methode
        }
        if(critere.getPkEvent()!=null){
            predicates.add(cbuild.like(evenementRoot.get("pkEvent"),"%" + critere.getPkEvent()+"%" ));
        }
        if(critere.getSecteur()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("secteur"), critere.getSecteur()));
        }
        if(critere.getNatureEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("natureEvent"), critere.getNatureEvent()));
        }
        if(critere.getCauseEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("causeEvent"), critere.getCauseEvent()));
        }
        if(critere.getEmisPar()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("emisPar"), critere.getEmisPar()));
        }
        if(critere.getDateEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("dateEvent"), critere.getDateEvent()));
        }
        if(critere.getStatutEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("statutEvent"),critere.getStatutEvent()));
        }
        if(critere.getTypeVehicule()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("typeVehicule"),critere.getTypeVehicule()));
        }

        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
        cquery.orderBy(cbuild.desc(evenementRoot.get("dateEvent")));



        // This query fetches the Books as per the Page Limit
        List<Evenement> result = entityManager.createQuery(cquery).setFirstResult((int) paging.getOffset()).setMaxResults(paging.getPageSize()).getResultList();

        // Create Count Query
        CriteriaQuery<Long> countQuery = cbuild.createQuery(Long.class);
//        Root<Remorquage> remorquageRootCount = countQuery.from(Remorquage.class);
        countQuery.select(cbuild.count(countQuery.from(Evenement.class))).where(predicates.toArray(new Predicate[0]));

        // Fetches the count of all Books as per given criteria
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<Evenement> pageResult = new PageImpl<>(result, paging, count);

        return pageResult;
    }

    @Override
    public List<Evenement> searchEvenementExcel(EventSearchExcelRequestDto critere) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evenement> cquery = cbuild.createQuery(Evenement.class);
        Root<Evenement> evenementRoot = cquery.from(Evenement.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getMatVehicule()!=null){
            predicates.add(cbuild.like(evenementRoot.get("matVehicule"),"%" + critere.getMatVehicule()+"%" )); //root methode
        }
        if(critere.getPkEvent()!=null){
            predicates.add(cbuild.like(evenementRoot.get("pkEvent"),"%" + critere.getPkEvent()+"%" ));
        }
        if(critere.getSecteur()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("secteur"), critere.getSecteur()));
        }
        if(critere.getNatureEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("natureEvent"), critere.getNatureEvent()));
        }
        if(critere.getCauseEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("causeEvent"), critere.getCauseEvent()));
        }
        if(critere.getEmisPar()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("emisPar"), critere.getEmisPar()));
        }
        if(critere.getDateEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("dateEvent"), critere.getDateEvent()));
        }
        if(critere.getStatutEvent()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("statutEvent"),critere.getStatutEvent()));
        }
        if(critere.getTypeVehicule()!=null){
            predicates.add(cbuild.equal(evenementRoot.get("typeVehicule"),critere.getTypeVehicule()));
        }

        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
        cquery.orderBy(cbuild.desc(evenementRoot.get("dateEvent")));

        List<Evenement> result = entityManager.createQuery(cquery).getResultList();
        return result;
    }

}
