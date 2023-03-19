package sn.sastrans.backofficev2.report.repositories;

import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.report.dtos.EventReportExcelRequest;
import sn.sastrans.backofficev2.report.dtos.EventReportRequest;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.List;


public interface EvenementReportRepositoryCustom {

    Page<Evenement> searchEvenement(EventReportRequest critere,int page, int size);

    List<Evenement> searchEventReportExcel(EventReportExcelRequest critere);
}
