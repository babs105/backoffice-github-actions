package sn.sastrans.backofficev2.trace.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.IntruSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Intrusion;
import sn.sastrans.backofficev2.trace.repositories.IntrusionRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class IntrusionRepositoryImpl implements IntrusionRepositoryCustom {

    @Autowired
   private  EntityManager entityManager;

    @Override
    public  Page<Intrusion>  searchIntrusion(IntruSearchRequestDto critere, int page, int size) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Intrusion> cquery = cbuild.createQuery(Intrusion.class);
        Root<Intrusion> IntrusionRoot = cquery.from(Intrusion.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

//        if(critere.getPka()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("evenement").get("localisation"),"%" + critere.getPka() + "%")); //root methode
//        }
//        if(critere.getSecteur()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("evenement").get("localisation"),"%" + critere.getSecteur() + "%"));
//        }
//        if(critere.getCatVhlRemorque()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
//        }
//        if(critere.getMatVhlRemorque()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
//        }
//        if(critere.getMatriculeDep()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
//        }
//        if(critere.getNomROM()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
//        }
//        if(critere.getLieuDepot()!=null){
//            predicates.add(cbuild.equal(IntrusionRoot.get("lieuDepot"),critere.getLieuDepot()));
//        }
//        if(critere.getStatutRom()!=null){
//            predicates.add(cbuild.equal(IntrusionRoot.get("statutRom"),critere.getStatutRom()));
//        }
//        if(critere.getLieuDepart()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
//        }
//        if(critere.getDateRom()!=null){
//            predicates.add(cbuild.equal(IntrusionRoot.get("date"),critere.getDateRom()));
//        }

//        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
//        cquery.orderBy(cbuild.desc(IntrusionRoot.get("date")));
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        // This query fetches the Books as per the Page Limit
//        List<Intrusion> result = entityManager.createQuery(cquery).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
//
//        // Create Count Query
//        CriteriaQuery<Long> countQuery = cbuild.createQuery(Long.class);
////        Root<Intrusion> IntrusionRootCount = countQuery.from(Intrusion.class);
//        countQuery.select(cbuild.count(countQuery.from(Intrusion.class))).where(predicates.toArray(new Predicate[0]));
//
//        // Fetches the count of all Books as per given criteria
//        Long count = entityManager.createQuery(countQuery).getSingleResult();
//
//        Page<Intrusion> pageResult = new PageImpl<>(result, pageable, count);
//
//        return pageResult;
       return null;
    }


    @Override
    public List<Intrusion> searchIntrusionExcel(IntruSearchExcelRequestDto critere) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Intrusion> cquery = cbuild.createQuery(Intrusion.class);
        Root<Intrusion> IntrusionRoot = cquery.from(Intrusion.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

//        if(critere.getSecteur()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("localisation"),"%" + critere.getSecteur() + "%"));
//        }
//        if(critere.getCatVhlRemorque()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
//        }
//        if(critere.getMatVhlRemorque()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
//        }
//        if(critere.getMatriculeDep()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
//        }
//        if(critere.getNomROM()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
//        }
//        if(critere.getLieuDepot()!=null){
//            predicates.add(cbuild.equal(IntrusionRoot.get("lieuDepot"),critere.getLieuDepot()));
//        }
//        if(critere.getStatutRom()!=null){
//            predicates.add(cbuild.equal(IntrusionRoot.get("statutRom"),critere.getStatutRom()));
//        }
//        if(critere.getLieuDepart()!=null){
//            predicates.add(cbuild.like(IntrusionRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
//        }
//        if(critere.getDateRom()!=null){
//            predicates.add(cbuild.equal(IntrusionRoot.get("dateRom"),critere.getDateRom()));
//        }

//        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
//        cquery.orderBy(cbuild.desc(IntrusionRoot.get("dateRom")));

        List<Intrusion> result = entityManager.createQuery(cquery).getResultList();
        return result;
    }
}
