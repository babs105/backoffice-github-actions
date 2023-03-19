package sn.sastrans.backofficev2.trace.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.BalSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.BalSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Balayage;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.repositories.BalayageRepository;
import sn.sastrans.backofficev2.trace.services.BalayageService;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BalayageServiceImpl implements BalayageService {

    @Autowired
    BalayageRepository balayageRepository;
    @Override
    public Balayage saveBalayage(Balayage balayage) {
        if((balayage.getDatePose()!=null) && (balayage.getDateDepose() == null )){
            balayage.setEtatBalayage("En Cours");
        }
        if  ((balayage.getDatePose()!=null) && (balayage.getDateDepose()!= null )){
            balayage.setEtatBalayage("Terminer");
        }
        return balayageRepository.save(balayage);
    }

    @Override
    public List<Balayage> getAllBalayage() {
        return balayageRepository.findAll();
    }

    @Override
    public Page<Balayage> getAllBalayage(Pageable pageable) {
        return balayageRepository.findAll(pageable);
    }

    @Override
    public Page<Balayage> getBalayageByKeyword(String keyword, Pageable pageable) {
        return balayageRepository.findByKeyword(keyword, pageable);
    }


    @Override
    public Balayage getBalayageById(int id) {
        return balayageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBalayage(int id) {
        balayageRepository.deleteById(id);

    }

    @Override
    public Page<Balayage> searchBalayage(BalSearchRequestDto critere, int page, int size) {
        return balayageRepository.searchBalayage(critere,page,size);
    }

    @Override
    public List<Balayage> searchBalayageExcel(BalSearchExcelRequestDto critere) {
        return balayageRepository.searchBalayageExcel(critere);
    }




}
