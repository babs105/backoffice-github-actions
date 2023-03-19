package sn.sastrans.backofficev2.report.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.sastrans.backofficev2.report.dtos.EvenementReportDto;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.Date;
import java.util.List;

public interface EvenementReportRepository extends JpaRepository<Evenement,Integer>,EvenementReportRepositoryCustom {

}
