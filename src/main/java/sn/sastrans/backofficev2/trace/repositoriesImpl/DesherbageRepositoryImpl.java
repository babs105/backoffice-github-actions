package sn.sastrans.backofficev2.trace.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.DeshSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.DeshSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Desherbage;
import sn.sastrans.backofficev2.trace.repositories.DesherbageRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class DesherbageRepositoryImpl implements DesherbageRepositoryCustom {

    @Autowired
   private  EntityManager entityManager;

    @Override
    public  Page<Desherbage>  searchDesherbage(DeshSearchRequestDto critere, int page, int size) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Desherbage> cquery = cbuild.createQuery(Desherbage.class);
        Root<Desherbage> DesherbageRoot = cquery.from(Desherbage.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

//        if(critere.getPka()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("evenement").get("localisation"),"%" + critere.getPka() + "%")); //root methode
//        }
//        if(critere.getSecteur()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("evenement").get("localisation"),"%" + critere.getSecteur() + "%"));
//        }
//        if(critere.getCatVhlRemorque()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
//        }
//        if(critere.getMatVhlRemorque()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
//        }
//        if(critere.getMatriculeDep()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
//        }
//        if(critere.getNomROM()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
//        }
//        if(critere.getLieuDepot()!=null){
//            predicates.add(cbuild.equal(DesherbageRoot.get("lieuDepot"),critere.getLieuDepot()));
//        }
//        if(critere.getStatutRom()!=null){
//            predicates.add(cbuild.equal(DesherbageRoot.get("statutRom"),critere.getStatutRom()));
//        }
//        if(critere.getLieuDepart()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
//        }
//        if(critere.getDateRom()!=null){
//            predicates.add(cbuild.equal(DesherbageRoot.get("date"),critere.getDateRom()));
//        }

//        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
//        cquery.orderBy(cbuild.desc(DesherbageRoot.get("date")));
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        // This query fetches the Books as per the Page Limit
//        List<Desherbage> result = entityManager.createQuery(cquery).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
//
//        // Create Count Query
//        CriteriaQuery<Long> countQuery = cbuild.createQuery(Long.class);
////        Root<Desherbage> DesherbageRootCount = countQuery.from(Desherbage.class);
//        countQuery.select(cbuild.count(countQuery.from(Desherbage.class))).where(predicates.toArray(new Predicate[0]));
//
//        // Fetches the count of all Books as per given criteria
//        Long count = entityManager.createQuery(countQuery).getSingleResult();
//
//        Page<Desherbage> pageResult = new PageImpl<>(result, pageable, count);
//
//        return pageResult;
       return null;
    }


    @Override
    public List<Desherbage> searchDesherbageExcel(DeshSearchExcelRequestDto critere) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Desherbage> cquery = cbuild.createQuery(Desherbage.class);
        Root<Desherbage> DesherbageRoot = cquery.from(Desherbage.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

//        if(critere.getSecteur()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("localisation"),"%" + critere.getSecteur() + "%"));
//        }
//        if(critere.getCatVhlRemorque()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
//        }
//        if(critere.getMatVhlRemorque()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
//        }
//        if(critere.getMatriculeDep()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
//        }
//        if(critere.getNomROM()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
//        }
//        if(critere.getLieuDepot()!=null){
//            predicates.add(cbuild.equal(DesherbageRoot.get("lieuDepot"),critere.getLieuDepot()));
//        }
//        if(critere.getStatutRom()!=null){
//            predicates.add(cbuild.equal(DesherbageRoot.get("statutRom"),critere.getStatutRom()));
//        }
//        if(critere.getLieuDepart()!=null){
//            predicates.add(cbuild.like(DesherbageRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
//        }
//        if(critere.getDateRom()!=null){
//            predicates.add(cbuild.equal(DesherbageRoot.get("dateRom"),critere.getDateRom()));
//        }

//        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
//        cquery.orderBy(cbuild.desc(DesherbageRoot.get("dateRom")));

        List<Desherbage> result = entityManager.createQuery(cquery).getResultList();
        return result;
    }
}
