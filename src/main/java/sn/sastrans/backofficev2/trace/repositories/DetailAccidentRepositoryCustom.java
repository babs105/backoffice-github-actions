package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.AcciSearchDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchDTO;
import sn.sastrans.backofficev2.trace.dto.RomSearchDto;
import sn.sastrans.backofficev2.trace.models.DetailAccident;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import java.util.List;


public interface DetailAccidentRepositoryCustom {

    Page<DetailAccident> searchDetailAccident(AcciSearchDto critere, int page, int size);

}
