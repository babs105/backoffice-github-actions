package sn.sastrans.backofficev2.report.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.report.dtos.EventReportExcelRequest;
import sn.sastrans.backofficev2.report.dtos.EventReportRequest;
import sn.sastrans.backofficev2.report.repositories.EvenementReportRepositoryCustom;
import sn.sastrans.backofficev2.trace.models.Evenement;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class EvenementReportRepositoryImpl implements EvenementReportRepositoryCustom {

    @Autowired
   private  EntityManager entityManager;

    @Override
    public Page<Evenement> searchEvenement(EventReportRequest critere,int page, int size) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evenement> cquery = cbuild.createQuery(Evenement.class);
        Root<Evenement> evenementReport = cquery.from(Evenement.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getDateDebut()!=null){
            predicates.add(cbuild.greaterThanOrEqualTo(evenementReport.get("dateEvent"), critere.getDateDebut())); //root methode
        }
        if(critere.getDateFin()!=null){
            predicates.add(cbuild.lessThanOrEqualTo(evenementReport.get("dateEvent"), critere.getDateFin())); //root methode
        }
        if(critere.getSecteur()!=null){
            predicates.add(cbuild.like(evenementReport.get("secteur"),"%" + critere.getSecteur() + "%"));
        }


        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
        cquery.orderBy(cbuild.desc(evenementReport.get("dateEvent")));

        Pageable pageable = PageRequest.of(page, size);

        // This query fetches the Books as per the Page Limit
        List<Evenement> result = entityManager.createQuery(cquery).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        // Create Count Query
        CriteriaQuery<Long> countQuery = cbuild.createQuery(Long.class);
//        Root<Remorquage> remorquageRootCount = countQuery.from(Remorquage.class);
        countQuery.select(cbuild.count(countQuery.from(Evenement.class))).where(predicates.toArray(new Predicate[0]));

        // Fetches the count of all Books as per given criteria
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<Evenement> pageResult = new PageImpl<>(result, pageable, count);

        return pageResult;
    }

    @Override
    public List<Evenement> searchEventReportExcel(EventReportExcelRequest critere) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Evenement> cquery = cbuild.createQuery(Evenement.class);
        Root<Evenement> evenementReport = cquery.from(Evenement.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getDateDebut()!=null){
            predicates.add(cbuild.greaterThanOrEqualTo(evenementReport.get("dateEvent"), critere.getDateDebut())); //root methode
        }
        if(critere.getDateFin()!=null){
            predicates.add(cbuild.lessThanOrEqualTo(evenementReport.get("dateEvent"), critere.getDateFin())); //root methode
        }
        if(critere.getSecteur()!=null){
            predicates.add(cbuild.like(evenementReport.get("secteur"),"%" + critere.getSecteur() + "%"));
        }


        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
        cquery.orderBy(cbuild.desc(evenementReport.get("dateEvent")));



        // This query fetches the Books as per the Page Limit
        List<Evenement> result = entityManager.createQuery(cquery).getResultList();





        return result;
    }


}
