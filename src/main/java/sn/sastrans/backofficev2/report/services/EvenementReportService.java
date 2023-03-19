package sn.sastrans.backofficev2.report.services;


import org.springframework.data.domain.Page;
import sn.sastrans.backofficev2.report.dtos.EventReportExcelRequest;
import sn.sastrans.backofficev2.report.dtos.EventReportRequest;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.Date;
import java.util.List;

public interface EvenementReportService {



       Page<Evenement> searchEventReport(EventReportRequest critre, int page, int size);

       List<Evenement> searchEventExcel(EventReportExcelRequest critere);
}
