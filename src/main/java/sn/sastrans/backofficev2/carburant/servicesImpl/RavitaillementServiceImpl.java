package sn.sastrans.backofficev2.carburant.servicesImpl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.carburant.models.Ravitaillement;
import sn.sastrans.backofficev2.carburant.repositories.RavitaillementRepository;
import sn.sastrans.backofficev2.carburant.services.CuveService;
import sn.sastrans.backofficev2.carburant.services.RavitaillementService;


import java.util.List;

@Slf4j
@Service
public class RavitaillementServiceImpl implements RavitaillementService {

    @Autowired
    private RavitaillementRepository ravitaillementRepository;
    @Autowired
    private CuveService cuveService;
    @Override
    @Transactional
    public Ravitaillement saveRavitaillement(Ravitaillement ravitaillement) {



//        ravitaillement.setMatriculeid(ravitaillement.getMatriculeid());


        return ravitaillementRepository.save(ravitaillement);
    }

    @Override
    public Page<Ravitaillement> getAllRavitaillement(Pageable pageable) {
        return ravitaillementRepository.findAll(pageable);
    }
    @Override
    public Page<Ravitaillement> getRavitaillementByKeyword(String keyword,Pageable pageable) {
        return ravitaillementRepository.findByKeyword(keyword,pageable);
    }

    @Override
    public Ravitaillement getRavitaillementById(int id) {
        return ravitaillementRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRavitaillement(int id) {
            ravitaillementRepository.deleteById(id);
    }

//    @Override
//    public List<Object> getConsoByVehiculeCurrentMonth() {
//        return ravitaillementRepository.getConsoByVehiculeCurrentMonth();
//    }

//    @Override
//    public List<Object> getConsoByVehiculeLastMonth() {
//        return ravitaillementRepository.getConsoByVehiculeLastMonth();
//    }


//    @Override
//    public List<Object> getConsoByMonthCurrentYear() {
//        return ravitaillementRepository.getConsoByMonthCurrentYear();
//    }
//
//    @Override
//    public List<Object> getConsoByMonthLastYear() {
//        return ravitaillementRepository.getConsoByMonthLastYear();
//    }
//
//    @Override
//    public List<Object> getConsoByVHlCategoryLastMonth() {
//        return ravitaillementRepository.getConsoByVHlCategoryLastMonth();
//    }
//
//    @Override
//    public List<Object> getConsoByVHlCategoryCurrentMonth() {
//        return ravitaillementRepository.getConsoByVHlCategoryCurrentMonth();
//    }
//
//    @Override
//    public List<Object> getConsoByServiceCurrentMonth() {
//        return ravitaillementRepository.getConsoByserviceCurrentMonth();
//    }
//
//    @Override
//    public List<Object> getConsoByServiceLastMonth() {
//        return ravitaillementRepository.getConsoByServiceLastMonth();
//    }
}
