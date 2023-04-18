package sn.sastrans.backofficev2.trace.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.DeshSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.DeshSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Desherbage;
import sn.sastrans.backofficev2.trace.repositories.DesherbageRepository;
import sn.sastrans.backofficev2.trace.services.DesherbageService;

import java.util.List;

@Slf4j
@Service
public class DesherbageServiceImpl implements DesherbageService {

    @Autowired
    DesherbageRepository desherbageRepository;
    @Override
    public Desherbage saveDesherbage(Desherbage desherbage) {
        if(!(desherbage.getHeureDebut().isEmpty()) && (desherbage.getHeureFin().isEmpty())){
            desherbage.setEtatDesherbage("En Cours");
        } else if  (!(desherbage.getHeureDebut().isEmpty()) && !(desherbage.getHeureFin().isEmpty())){

            desherbage.setEtatDesherbage("Terminer");
        }
        return desherbageRepository.save(desherbage);
    }


    @Override
    public List<Desherbage> getAllDesherbage() {
        return desherbageRepository.findAll();
    }

    @Override
    public Page<Desherbage> getAllDesherbage(Pageable pageable) {
        return desherbageRepository.findAll(pageable);
    }

    @Override
    public Page<Desherbage> getDesherbageByKeyword(String keyword, Pageable pageable) {
        return desherbageRepository.findByKeyword(keyword, pageable);
    }


    @Override
    public Desherbage getDesherbageById(int id) {
        return desherbageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDesherbage(int id) {
        desherbageRepository.deleteById(id);

    }

    @Override
    public Page<Desherbage> searchDesherbage(DeshSearchRequestDto critere, int page, int size) {
        return desherbageRepository.searchDesherbage(critere,page,size);
    }

    @Override
    public List<Desherbage> searchDesherbageExcel(DeshSearchExcelRequestDto critere) {
        return desherbageRepository.searchDesherbageExcel(critere);
    }




}
