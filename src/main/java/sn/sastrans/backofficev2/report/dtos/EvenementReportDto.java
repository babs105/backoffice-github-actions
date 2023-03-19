package sn.sastrans.backofficev2.report.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class EvenementReportDto {
    private Integer id;
    private Date dateEvent;
    private String natureEvent;
    private String causeEvent;
    private String typeVehicule;
    private String matVehicule;
    private String localisation;
    private String emisPar;
    private String heureDebutEvent;
    private String heureAppelOPC;
    private String heureAppelPAT;
    private String nomPAT;
    private String heureArriveePAT;
    private String dateheurePoseBalise;
    private String  dateheureDeposeBalise;
    private String typeBalisage;
    private String pkDebutBalisage;
    private String pkFinBalisage;
    private String statutEvent;
    private String matVhlRemorque;
    private String catVhlRemorque;
    private String observation;




}
//    @Query("SELECT new com.roytuts.spring.data.jpa.left.right.inner.cross.join.dto.DeptEmpDto(d.name, e.name, e.email, e.address) "
//            + "FROM Department d LEFT JOIN d.employees e")
//    List<DeptEmpDto> fetchEmpDeptDataLeftJoin();