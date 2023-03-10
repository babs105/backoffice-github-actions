package sn.sastrans.backofficev2.trace.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.RomSearchDto;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.repositories.RemorquageRepository;
import sn.sastrans.backofficev2.trace.services.RemorquageService;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class RemorquageServiceImpl implements RemorquageService {

    @Autowired
    RemorquageRepository remorquageRepository;
    @Override
    public Remorquage saveRemorquage(Remorquage remorquage) {

        return remorquageRepository.save(remorquage);
    }

    @Override
    public List<Remorquage> getAllRemorquage() {
        return remorquageRepository.findAll();
    }

    @Override
    public Page<Remorquage> findPage(int pageNumber) {
        Pageable  pageable = PageRequest.of(pageNumber-1,50);
        return remorquageRepository.findAll(pageable);
    }

    @Override
    public Page<Remorquage> getAllRemorquageWithSort(String keyword, Date dateDebut, Date dateFin, String field, String direction, int pageNumber) {
        Sort sort=direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,50,sort);
        if((keyword==null || keyword.isEmpty())  && dateDebut==null && dateFin==null){
            log.info("PAS DE FILTER");
            return remorquageRepository.findAll(PageRequest.of(pageNumber-1,50,Sort.by("dateRom").descending()));
        }else{
            log.info("FILTERRR");
            return remorquageRepository.findAll(keyword,dateDebut,dateFin,pageable);
//            return getRemorquageByKeyword(keyword,pageable);
        }
    }

    @Override
    public Page<Remorquage> getRemorquageByKeyword(String keyword, Pageable pageable) {
        return remorquageRepository.findByKeyword(keyword,pageable);
    }

    @Override
    public Remorquage getRemorquageById(int id) {
        return remorquageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRemorquage(int id) {
        remorquageRepository.deleteById(id);

    }

    @Override
    public Page<Remorquage> searchRemorquage(RomSearchDto critere,int page,int size) {
        return remorquageRepository.searchRemorquage(critere,page,size);
    }

    @Override
    public List<Object> getNombreRomByCatAmtLastMonth() {
        return remorquageRepository.getNombreRomByCatAmtLastMonth();
    }

    @Override
    public List<Object> getNombreRomByCatAmtCurrentMonth() {
        return remorquageRepository.getNombreRomByCatAmtCurrentMonth();
    }

    @Override
    public List<Object> getNombreRomByCatTTLastMonth() {
        return remorquageRepository.getNombreRomByCatTTLastMonth();
    }

    @Override
    public List<Object> getNombreRomByCatTTCurrentMonth() {
        return remorquageRepository.getNombreRomByCatTTCurrentMonth();
    }
}
