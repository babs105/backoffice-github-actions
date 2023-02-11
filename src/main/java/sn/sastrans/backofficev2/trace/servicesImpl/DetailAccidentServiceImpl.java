package sn.sastrans.backofficev2.trace.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.models.DetailAccident;
import sn.sastrans.backofficev2.trace.repositories.DetailAccidentRepository;
import sn.sastrans.backofficev2.trace.services.DetailAccidentService;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DetailAccidentServiceImpl implements DetailAccidentService {

    @Autowired
    DetailAccidentRepository detailAccidentRepository;
    @Override
    public DetailAccident saveDetailAccident(DetailAccident DetailAccident) {


        return detailAccidentRepository.save(DetailAccident);
    }

    @Override
    public List<DetailAccident> getAllDetailAccident() {
        return detailAccidentRepository.findAll();
    }

    @Override
    public List<DetailAccident> getDetailAccidentByEventId(Integer id) {
        return detailAccidentRepository.findByEventid(id);
    }

    @Override
    public Page<DetailAccident> findPage(int pageNumber) {
        Pageable  pageable = PageRequest.of(pageNumber-1,50);
        return detailAccidentRepository.findAll(pageable);
    }

    @Override
    public Page<DetailAccident> getAllDetailAccidentWithSort(String keyword, Date dateDebut, Date dateFin, String field, String direction, int pageNumber) {
        Sort sort=direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,50,sort);
        if((keyword==null || keyword.isEmpty())  && dateDebut==null && dateFin==null){
            log.info("PAS DE FILTER");
            return detailAccidentRepository.findAll(PageRequest.of(pageNumber-1,50,Sort.by("typeAccident").ascending()));
        }else{
            log.info("FILTERRR");
            return detailAccidentRepository.findAll(keyword,dateDebut,dateFin,pageable);
//            return getRemorquageByKeyword(keyword,pageable);
        }
    }

    @Override
    public Page<DetailAccident> getDetailAccidentByKeyword(String keyword, Pageable pageable) {
        return detailAccidentRepository.findByKeyword(keyword,pageable);
    }

    @Override
    public DetailAccident getDetailAccidentById(int id) {
        return detailAccidentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDetailAccident(Integer id) {
        log.info("delete accident id: "+id);
        detailAccidentRepository.deleteById(id);

    }
}
