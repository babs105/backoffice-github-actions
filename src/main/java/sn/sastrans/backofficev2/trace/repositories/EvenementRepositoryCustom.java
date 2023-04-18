package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.trace.dto.EventSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.RomSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.List;


public interface EvenementRepositoryCustom {

    Page<Evenement> searchEvenement(EventSearchRequestDto critere, Pageable paging);
    List<Evenement> searchEvenementExcel(EventSearchExcelRequestDto critere);

}
