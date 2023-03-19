package sn.sastrans.backofficev2.trace.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.NetSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Nettoyage;
import sn.sastrans.backofficev2.trace.repositories.NettoyageRepository;
import sn.sastrans.backofficev2.trace.services.NettoyageService;

import java.util.List;

@Slf4j
@Service
public class NettoyageServiceImpl implements NettoyageService {

    @Autowired
    NettoyageRepository nettoyageRepository;
    @Override
    public Nettoyage saveNettoyage(Nettoyage nettoyage) {
        if((nettoyage.getDatePose()!=null) && (nettoyage.getDateDepose() == null )){
            nettoyage.setEtatNettoyage("En Cours");
        }
        if  ((nettoyage.getDatePose()!=null) && (nettoyage.getDateDepose()!= null )){
            nettoyage.setEtatNettoyage("Terminer");
        }
        return nettoyageRepository.save(nettoyage);
    }

    @Override
    public List<Nettoyage> getAllNettoyage() {
        return nettoyageRepository.findAll();
    }

    @Override
    public Page<Nettoyage> getAllNettoyage(Pageable pageable) {
        return nettoyageRepository.findAll(pageable);
    }

    @Override
    public Page<Nettoyage> getNettoyageByKeyword(String keyword, Pageable pageable) {
        return nettoyageRepository.findByKeyword(keyword, pageable);
    }


    @Override
    public Nettoyage getNettoyageById(int id) {
        return nettoyageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteNettoyage(int id) {
        nettoyageRepository.deleteById(id);

    }

    @Override
    public Page<Nettoyage> searchNettoyage(NetSearchRequestDto critere, int page, int size) {
        return nettoyageRepository.searchNettoyage(critere,page,size);
    }

    @Override
    public List<Nettoyage> searchNettoyageExcel(NetSearchExcelRequestDto critere) {
        return nettoyageRepository.searchNettoyageExcel(critere);
    }




}
