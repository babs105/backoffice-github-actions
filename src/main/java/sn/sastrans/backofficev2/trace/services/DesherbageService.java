package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.DeshSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.DeshSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Desherbage;

import java.util.List;

@Service
public interface DesherbageService {
    Desherbage saveDesherbage(Desherbage desherbage);
    List<Desherbage> getAllDesherbage();
    Page<Desherbage> getAllDesherbage(Pageable pageable);
    Page<Desherbage> getDesherbageByKeyword(String keyword, Pageable pageable);
    Desherbage getDesherbageById(int id);
    void deleteDesherbage(int id);
    Page<Desherbage> searchDesherbage(DeshSearchRequestDto critre, int page, int size);
    List<Desherbage> searchDesherbageExcel(DeshSearchExcelRequestDto critere);



}
