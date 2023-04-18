package sn.sastrans.backofficev2.trace.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.IntruSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Intrusion;
import sn.sastrans.backofficev2.trace.repositories.IntrusionRepository;
import sn.sastrans.backofficev2.trace.services.IntrusionService;

import java.util.List;

@Slf4j
@Service
public class IntrusionServiceImpl implements IntrusionService {

    @Autowired
    IntrusionRepository intrusionRepository;
    @Override
    public Intrusion saveIntrusion(Intrusion intrusion) {
        if(!(intrusion.getHeureAnnonce().isEmpty()) && (intrusion.getHeureFin().isEmpty())){
            intrusion.setEtatIntrusion("En Cours");
        } else if  (!(intrusion.getHeureAnnonce().isEmpty()) && !(intrusion.getHeureFin().isEmpty())){

            intrusion.setEtatIntrusion("Terminer");
        }
        return intrusionRepository.save(intrusion);
    }


    @Override
    public List<Intrusion> getAllIntrusion() {
        return intrusionRepository.findAll();
    }

    @Override
    public Page<Intrusion> getAllIntrusion(Pageable pageable) {
        return intrusionRepository.findAll(pageable);
    }

    @Override
    public Page<Intrusion> getIntrusionByKeyword(String keyword, Pageable pageable) {
        return intrusionRepository.findByKeyword(keyword, pageable);
    }


    @Override
    public Intrusion getIntrusionById(int id) {
        return intrusionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteIntrusion(int id) {
        intrusionRepository.deleteById(id);

    }

    @Override
    public Page<Intrusion> searchIntrusion(IntruSearchRequestDto critere, int page, int size) {
        return intrusionRepository.searchIntrusion(critere,page,size);
    }

    @Override
    public List<Intrusion> searchIntrusionExcel(IntruSearchExcelRequestDto critere) {
        return intrusionRepository.searchIntrusionExcel(critere);
    }




}
