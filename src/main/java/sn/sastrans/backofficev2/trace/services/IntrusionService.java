package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.IntruSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Intrusion;

import java.util.List;

@Service
public interface IntrusionService {
    Intrusion saveIntrusion(Intrusion intrusion);
    List<Intrusion> getAllIntrusion();
    Page<Intrusion> getAllIntrusion(Pageable pageable);
    Page<Intrusion> getIntrusionByKeyword(String keyword, Pageable pageable);
    Intrusion getIntrusionById(int id);
    void deleteIntrusion(int id);
    Page<Intrusion> searchIntrusion(IntruSearchRequestDto critre, int page, int size);
    List<Intrusion> searchIntrusionExcel(IntruSearchExcelRequestDto critere);



}
