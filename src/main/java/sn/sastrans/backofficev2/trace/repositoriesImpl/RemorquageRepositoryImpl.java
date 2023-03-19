package sn.sastrans.backofficev2.trace.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.trace.dto.RomSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.repositories.RemorquageRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class RemorquageRepositoryImpl implements RemorquageRepositoryCustom {

    @Autowired
   private  EntityManager entityManager;

    @Override
    public  Page<Remorquage>  searchRemorquage(RomSearchRequestDto critere, int page, int size) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Remorquage> cquery = cbuild.createQuery(Remorquage.class);
        Root<Remorquage> remorquageRoot = cquery.from(Remorquage.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getPka()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("evenement").get("localisation"),"%" + critere.getPka() + "%")); //root methode
        }
        if(critere.getSecteur()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("evenement").get("localisation"),"%" + critere.getSecteur() + "%"));
        }
        if(critere.getCatVhlRemorque()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
        }
        if(critere.getMatVhlRemorque()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
        }
        if(critere.getMatriculeDep()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
        }
        if(critere.getNomROM()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
        }
        if(critere.getLieuDepot()!=null){
            predicates.add(cbuild.equal(remorquageRoot.get("lieuDepot"),critere.getLieuDepot()));
        }
        if(critere.getStatutRom()!=null){
            predicates.add(cbuild.equal(remorquageRoot.get("statutRom"),critere.getStatutRom()));
        }
        if(critere.getLieuDepart()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
        }
        if(critere.getDateRom()!=null){
            predicates.add(cbuild.equal(remorquageRoot.get("dateRom"),critere.getDateRom()));
        }

        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
        cquery.orderBy(cbuild.desc(remorquageRoot.get("dateRom")));

        Pageable pageable = PageRequest.of(page, size);

        // This query fetches the Books as per the Page Limit
        List<Remorquage> result = entityManager.createQuery(cquery).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        // Create Count Query
        CriteriaQuery<Long> countQuery = cbuild.createQuery(Long.class);
//        Root<Remorquage> remorquageRootCount = countQuery.from(Remorquage.class);
        countQuery.select(cbuild.count(countQuery.from(Remorquage.class))).where(predicates.toArray(new Predicate[0]));

        // Fetches the count of all Books as per given criteria
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<Remorquage> pageResult = new PageImpl<>(result, pageable, count);

        return pageResult;

    }

    @Override
    public List<Remorquage> searchRemorquageExcel(RomSearchExcelRequestDto critere) {
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();
        CriteriaQuery<Remorquage> cquery = cbuild.createQuery(Remorquage.class);
        Root<Remorquage> remorquageRoot = cquery.from(Remorquage.class); // ou sera executer le query

        List<Predicate> predicates = new ArrayList<>();

        if(critere.getPka()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("evenement").get("localisation"),"%" + critere.getPka() + "%")); //root methode
        }
        if(critere.getSecteur()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("evenement").get("localisation"),"%" + critere.getSecteur() + "%"));
        }
        if(critere.getCatVhlRemorque()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("catVhlRemorque"),"%" + critere.getCatVhlRemorque() + "%"));
        }
        if(critere.getMatVhlRemorque()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("matVhlRemorque"),"%" + critere.getMatVhlRemorque() + "%"));
        }
        if(critere.getMatriculeDep()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("matriculeDep"),"%" + critere.getMatriculeDep() + "%"));
        }
        if(critere.getNomROM()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("nomROM"),"%" + critere.getNomROM() + "%"));
        }
        if(critere.getLieuDepot()!=null){
            predicates.add(cbuild.equal(remorquageRoot.get("lieuDepot"),critere.getLieuDepot()));
        }
        if(critere.getStatutRom()!=null){
            predicates.add(cbuild.equal(remorquageRoot.get("statutRom"),critere.getStatutRom()));
        }
        if(critere.getLieuDepart()!=null){
            predicates.add(cbuild.like(remorquageRoot.get("lieuDepart"),"%" + critere.getLieuDepart() + "%"));
        }
        if(critere.getDateRom()!=null){
            predicates.add(cbuild.equal(remorquageRoot.get("dateRom"),critere.getDateRom()));
        }

        cquery.where(predicates.toArray(new Predicate[0])); // convert predicat arraylist to array
        cquery.orderBy(cbuild.desc(remorquageRoot.get("dateRom")));

        List<Remorquage> result = entityManager.createQuery(cquery).getResultList();
        return result;
    }
}
