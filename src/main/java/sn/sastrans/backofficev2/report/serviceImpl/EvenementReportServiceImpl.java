package sn.sastrans.backofficev2.report.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.report.dtos.EventReportExcelRequest;
import sn.sastrans.backofficev2.report.dtos.EventReportRequest;
import sn.sastrans.backofficev2.report.repositories.EvenementReportRepository;
import sn.sastrans.backofficev2.report.services.EvenementReportService;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EvenementReportServiceImpl implements EvenementReportService {
    @Autowired
    EvenementReportRepository evenementReportRepository;

    @Override
    public Page<Evenement> searchEventReport(EventReportRequest critere, int page, int size){
    return   evenementReportRepository.searchEvenement(critere,page,size);
    }

    @Override
    public List<Evenement> searchEventExcel(EventReportExcelRequest critere) {

        return evenementReportRepository.searchEventReportExcel(critere);
    }



//    @Override
//    public List<EvenementReport> generateReport(Date dateDebut, Date dateFin) {
//
//        return evenementReportRepository.findAll(dateDebut,dateFin);
//    }
}
