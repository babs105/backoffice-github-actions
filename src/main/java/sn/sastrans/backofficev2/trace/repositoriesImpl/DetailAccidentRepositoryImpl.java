package sn.sastrans.backofficev2.trace.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.trace.dto.AcciSearchDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchDto;
import sn.sastrans.backofficev2.trace.models.DetailAccident;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.repositories.DetailAccidentRepositoryCustom;
import sn.sastrans.backofficev2.trace.repositories.RemorquageRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class DetailAccidentRepositoryImpl implements DetailAccidentRepositoryCustom {

    @Autowired
   private  EntityManager entityManager;

    @Override
    public  Page<DetailAccident>  searchDetailAccident(AcciSearchDto critere, int page, int size) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailAccident> cquery = cbuild.createQuery(DetailAccident.class);
        Root<DetailAccident> detailAccidentRoot = cquery.from(DetailAccident.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getPka()!=null){
            predicates.add(cbuild.like(detailAccidentRoot.get("evenement").get("localisation"),"%" + critere.getPka() + "%")); //root methode
        }
        if(critere.getSecteur()!=null){
            predicates.add(cbuild.like(detailAccidentRoot.get("evenement").get("localisation"),"%" + critere.getSecteur() + "%"));
        }
        if(critere.getCauseAccident()!=null){
            predicates.add(cbuild.like(detailAccidentRoot.get("causeAccident"),"%" + critere.getCauseAccident() + "%"));
        }
        if(critere.getMatriculeVhlImplique()!=null){
            predicates.add(cbuild.like(detailAccidentRoot.get("matriculeVhlImplique"),"%" + critere.getMatriculeVhlImplique()+ "%"));
        }
        if(critere.getTypeVhlImplique()!=null){
            predicates.add(cbuild.like(detailAccidentRoot.get("typeVhlImplique"),"%" + critere.getTypeVhlImplique() + "%"));
        }
        if(critere.getNbreBlesseLeger()!=null){
            predicates.add(cbuild.equal(detailAccidentRoot.get("nomROM"),critere.getNbreBlesseLeger()));
        }
        if(critere.getNbreMort()!=null){
            predicates.add(cbuild.equal(detailAccidentRoot.get("nbreMort"),critere.getNbreMort()));
        }
        if(critere.getTypeAccident()!=null){
            predicates.add(cbuild.equal(detailAccidentRoot.get("typeAccident"),critere.getTypeAccident()));
        }
        if(critere.getNbreBlesseGrave()!=null){
            predicates.add(cbuild.equal(detailAccidentRoot.get("nbreBlesseGrave"),critere.getNbreBlesseGrave()));
        }
        if(critere.getDateAcci()!=null){
            predicates.add(cbuild.equal(detailAccidentRoot.get("dateAcci"),critere.getDateAcci()));
        }

        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
        cquery.orderBy(cbuild.desc(detailAccidentRoot.get("dateAcci")));

        Pageable pageable = PageRequest.of(page, size);

        // This query fetches the Books as per the Page Limit
        List<DetailAccident> result = entityManager.createQuery(cquery).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        // Create Count Query
        CriteriaQuery<Long> countQuery = cbuild.createQuery(Long.class);
//        Root<Remorquage> detailAccidentRootCount = countQuery.from(Remorquage.class);
        countQuery.select(cbuild.count(countQuery.from(DetailAccident.class))).where(predicates.toArray(new Predicate[0]));

        // Fetches the count of all Books as per given criteria
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<DetailAccident> pageResult = new PageImpl<>(result, pageable, count);

        return pageResult;
//        return entityManager.createQuery(cquery).getResultList();
    }
}
