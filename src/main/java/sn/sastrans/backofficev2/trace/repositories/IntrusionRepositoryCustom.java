package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.IntruSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Intrusion;

import java.util.List;


public interface IntrusionRepositoryCustom {

    Page<Intrusion> searchIntrusion(IntruSearchRequestDto critere, int page, int size);


    List<Intrusion> searchIntrusionExcel(IntruSearchExcelRequestDto critere);


}
