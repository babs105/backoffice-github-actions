package sn.sastrans.backofficev2.carburant.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.carburant.models.Rajout;
import sn.sastrans.backofficev2.carburant.repositories.RajoutRepository;
import sn.sastrans.backofficev2.carburant.services.CuveService;
import sn.sastrans.backofficev2.carburant.services.RajoutService;

import java.util.List;

@Service
public class RajoutServiceImpl implements RajoutService {

    @Autowired
    private RajoutRepository rajoutRepository;

    @Autowired
    private CuveService cuveService;
    @Override
    public Rajout saveRajout(Rajout rajout) {

        Cuve cuve = cuveService.getCuveByName(rajout.getCuveid());
        if(cuve.getQuantity()>0){
            cuve.setQuantity(cuve.getQuantity() + rajout.getQuantityRajout());
        }else{
            cuve.setQuantity(rajout.getQuantityRajout());
        }

        cuve = cuveService.saveCuve(cuve);
        if(cuve != null){
            return rajoutRepository.save(rajout);
        }
        return null;

    }

    @Override
    public List<Rajout> getAllRajout() {
        return rajoutRepository.findAll();
    }

    @Override
    public Rajout getRajoutById(int id) {
        return rajoutRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRajout(int id) {
        rajoutRepository.deleteById(id);
    }
}
