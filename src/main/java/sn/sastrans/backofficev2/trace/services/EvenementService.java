package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.trace.dto.EventSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.List;

public interface EvenementService {
    Evenement saveEvenement(Evenement evenement);
    Page<Evenement> getAllEvenement(Pageable pageable);
    Page<Evenement> getAllEvenementByKeyword(String keyword,Pageable pageable);
    Evenement getEvenementById(Integer id);

    Page<Evenement> searchEvenement(EventSearchRequestDto critere,Pageable pageable);
    Evenement deleteEvenement(Integer id);
    Evenement updateEvenement(Integer id, Evenement event);
    List<Evenement> searchEvenementExcel(EventSearchExcelRequestDto critere);
}
