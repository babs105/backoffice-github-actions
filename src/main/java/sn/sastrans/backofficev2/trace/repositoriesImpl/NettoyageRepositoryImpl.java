package sn.sastrans.backofficev2.trace.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.trace.dto.BalSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.BalSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Nettoyage;
import sn.sastrans.backofficev2.trace.repositories.NettoyageRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class NettoyageRepositoryImpl implements NettoyageRepositoryCustom {

    @Autowired
   private  EntityManager entityManager;

    @Override
    public  Page<Nettoyage>  searchNettoyage(NetSearchRequestDto critere, int page, int size) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Nettoyage> cquery = cbuild.createQuery(Nettoyage.class);
        Root<Nettoyage> nettoyageRoot = cquery.from(Nettoyage.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

//        if(critere.getPka()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("evenement").get("localisation"),"%" + critere.getPka() + "%")); //root methode
//        }
//        if(critere.getSecteur()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("evenement").get("localisation"),"%" + critere.getSecteur() + "%"));
//        }
//        if(critere.getCatVhlRemorque()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
//        }
//        if(critere.getMatVhlRemorque()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
//        }
//        if(critere.getMatriculeDep()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
//        }
//        if(critere.getNomROM()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
//        }
//        if(critere.getLieuDepot()!=null){
//            predicates.add(cbuild.equal(nettoyageRoot.get("lieuDepot"),critere.getLieuDepot()));
//        }
//        if(critere.getStatutRom()!=null){
//            predicates.add(cbuild.equal(nettoyageRoot.get("statutRom"),critere.getStatutRom()));
//        }
//        if(critere.getLieuDepart()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
//        }
//        if(critere.getDateRom()!=null){
//            predicates.add(cbuild.equal(nettoyageRoot.get("date"),critere.getDateRom()));
//        }

//        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
//        cquery.orderBy(cbuild.desc(nettoyageRoot.get("date")));
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        // This query fetches the Books as per the Page Limit
//        List<Nettoyage> result = entityManager.createQuery(cquery).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
//
//        // Create Count Query
//        CriteriaQuery<Long> countQuery = cbuild.createQuery(Long.class);
////        Root<Nettoyage> nettoyageRootCount = countQuery.from(Nettoyage.class);
//        countQuery.select(cbuild.count(countQuery.from(Nettoyage.class))).where(predicates.toArray(new Predicate[0]));
//
//        // Fetches the count of all Books as per given criteria
//        Long count = entityManager.createQuery(countQuery).getSingleResult();
//
//        Page<Nettoyage> pageResult = new PageImpl<>(result, pageable, count);
//
//        return pageResult;
       return null;
    }


    @Override
    public List<Nettoyage> searchNettoyageExcel(NetSearchExcelRequestDto critere) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Nettoyage> cquery = cbuild.createQuery(Nettoyage.class);
        Root<Nettoyage> nettoyageRoot = cquery.from(Nettoyage.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

//        if(critere.getSecteur()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("localisation"),"%" + critere.getSecteur() + "%"));
//        }
//        if(critere.getCatVhlRemorque()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
//        }
//        if(critere.getMatVhlRemorque()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
//        }
//        if(critere.getMatriculeDep()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
//        }
//        if(critere.getNomROM()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
//        }
//        if(critere.getLieuDepot()!=null){
//            predicates.add(cbuild.equal(nettoyageRoot.get("lieuDepot"),critere.getLieuDepot()));
//        }
//        if(critere.getStatutRom()!=null){
//            predicates.add(cbuild.equal(nettoyageRoot.get("statutRom"),critere.getStatutRom()));
//        }
//        if(critere.getLieuDepart()!=null){
//            predicates.add(cbuild.like(nettoyageRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
//        }
//        if(critere.getDateRom()!=null){
//            predicates.add(cbuild.equal(nettoyageRoot.get("dateRom"),critere.getDateRom()));
//        }

//        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
//        cquery.orderBy(cbuild.desc(nettoyageRoot.get("dateRom")));

        List<Nettoyage> result = entityManager.createQuery(cquery).getResultList();
        return result;
    }
}
