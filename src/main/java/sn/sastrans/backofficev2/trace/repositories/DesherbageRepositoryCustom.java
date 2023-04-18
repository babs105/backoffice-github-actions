package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.DeshSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.DeshSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.IntruSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Desherbage;

import java.util.List;


public interface DesherbageRepositoryCustom {

    Page<Desherbage> searchDesherbage(DeshSearchRequestDto critere, int page, int size);


    List<Desherbage> searchDesherbageExcel(DeshSearchExcelRequestDto critere);


}
