package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.RomSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.Date;
import java.util.List;
@Service
public interface RemorquageService {
    Remorquage saveRemorquage(Remorquage remorquage);
    List<Remorquage> getAllRemorquage();

    Page<Remorquage> findPage(int pageNumber);
    Page<Remorquage> getAllRemorquageWithSort(String keyword, Date dateDebut, Date dateFin, String field, String direction, int pageNumber) ;
    Page<Remorquage> getRemorquageByKeyword(String keyword, Pageable pageable);
    Remorquage getRemorquageById(int id);
    void deleteRemorquage(int id);
    Page<Remorquage> searchRemorquage(RomSearchRequestDto critre, int page, int size);
    List<Remorquage> searchRemorquageExcel(RomSearchExcelRequestDto critere);


    List<Object> getNombreRomByCatAmtLastMonth();
    List<Object> getNombreRomByCatAmtCurrentMonth();

    List<Object> getNombreRomByCatTTLastMonth();
    List<Object> getNombreRomByCatTTCurrentMonth();


}
