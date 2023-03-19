package sn.sastrans.backofficev2.carburant.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.carburant.models.Ravitaillement;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.List;

public interface RavitaillementService {
    Ravitaillement saveRavitaillement(Ravitaillement ravitaillement);
    Page<Ravitaillement> getAllRavitaillement(Pageable pageable);

    Page<Ravitaillement> getRavitaillementByKeyword(String keyword, Pageable pageable);
    Ravitaillement getRavitaillementById(int id);
    void deleteRavitaillement(int id);
//    List<Object> getConsoByVehiculeCurrentMonth();
//    List<Object> getConsoByVehiculeLastMonth();
//
////    List<Object> getConsoBydate();
//    List<Object>  getConsoByMonthCurrentYear();
//    List<Object>  getConsoByMonthLastYear();
//    List<Object> getConsoByVHlCategoryLastMonth();
//
//    List<Object>  getConsoByVHlCategoryCurrentMonth();

//    List<Object> getConsoByServiceCurrentMonth();
//
//    List<Object> getConsoByServiceLastMonth();

}
