package sn.sastrans.backofficev2.trace.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.trace.dto.AcciSearchDto;
import sn.sastrans.backofficev2.trace.models.DetailAccident;


public interface DetailAccidentRepositoryCustom {

    Page<DetailAccident> searchDetailAccident(AcciSearchDto critere, int page, int size);

}
