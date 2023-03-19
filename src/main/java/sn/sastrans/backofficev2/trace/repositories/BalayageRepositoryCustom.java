package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.*;
import sn.sastrans.backofficev2.trace.models.Balayage;

import java.util.List;


public interface BalayageRepositoryCustom {

    Page<Balayage> searchBalayage(BalSearchRequestDto critere, int page, int size);


    List<Balayage> searchBalayageExcel(BalSearchExcelRequestDto critere);


}
