package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.BalSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.BalSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Nettoyage;

import java.util.List;

@Service
public interface NettoyageService {
    Nettoyage saveNettoyage(Nettoyage Nettoyage);
    List<Nettoyage> getAllNettoyage();
    Page<Nettoyage> getAllNettoyage(Pageable pageable);
    Page<Nettoyage> getNettoyageByKeyword(String keyword, Pageable pageable);
    Nettoyage getNettoyageById(int id);
    void deleteNettoyage(int id);
    Page<Nettoyage> searchNettoyage(NetSearchRequestDto critre, int page, int size);
    List<Nettoyage> searchNettoyageExcel(NetSearchExcelRequestDto critere);



}
