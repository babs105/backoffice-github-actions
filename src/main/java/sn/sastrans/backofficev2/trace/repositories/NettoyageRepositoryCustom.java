package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.BalSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.BalSearchRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.NetSearchRequestDto;
import sn.sastrans.backofficev2.trace.models.Nettoyage;

import java.util.List;


public interface NettoyageRepositoryCustom {

    Page<Nettoyage> searchNettoyage(NetSearchRequestDto critere, int page, int size);


    List<Nettoyage> searchNettoyageExcel(NetSearchExcelRequestDto critere);


}
