package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.BalSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.BalSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Balayage;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.Date;
import java.util.List;

@Service
public interface BalayageService {
    Balayage saveBalayage(Balayage Balayage);
    List<Balayage> getAllBalayage();
    Page<Balayage> getAllBalayage(Pageable pageable);
    Page<Balayage> getBalayageByKeyword(String keyword, Pageable pageable);
    Balayage getBalayageById(int id);
    void deleteBalayage(int id);
    Page<Balayage> searchBalayage(BalSearchRequestDto critre, int page, int size);
    List<Balayage> searchBalayageExcel(BalSearchExcelRequestDto critere);



}
