package sn.sastrans.backofficev2.carburant.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.carburant.models.Cuve;
import sn.sastrans.backofficev2.carburant.repositories.CuveRepository;
import sn.sastrans.backofficev2.carburant.services.CuveService;


@Service
public class CuveServiceImpl implements CuveService {

    @Autowired
    private CuveRepository cuveRepository;

    @Override
    public Cuve saveCuve(Cuve cuve) {
        return cuveRepository.save(cuve);
    }

    @Override
    public Page<Cuve> getAllCuve(Pageable pageable) {
        return cuveRepository.findAll(pageable);
    }

    @Override
    public Page<Cuve> getAllCuveByKeyword(String keyword, Pageable pageable) {
        return cuveRepository.findByKeyword(keyword,pageable);
    }


    @Override
    public Cuve getCuveById(int id) {
        return cuveRepository.findById(id).orElse(null);
    }

    @Override
    public Cuve getCuveByName(String cuvename) {
        return cuveRepository.findByCuveName(cuvename);
    }

    @Override
    public void deleteCuve(int id) {
        cuveRepository.deleteById(id);
    }

}
