package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.RomSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.List;

public interface RemorquageRepositoryCustom  {
    Page<Remorquage> searchRemorquage(RomSearchRequestDto critere, int page, int size);


    List<Remorquage> searchRemorquageExcel(RomSearchExcelRequestDto critere);
}


// between ?1 and ?2  OR e.dateEvent >= ?2 AND e.dateEvent <= ?3