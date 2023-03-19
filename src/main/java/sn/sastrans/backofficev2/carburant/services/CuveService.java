package sn.sastrans.backofficev2.carburant.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.carburant.models.Cuve;

import java.util.List;

public interface CuveService {
    Cuve saveCuve(Cuve cuve);
    Page<Cuve> getAllCuve(Pageable pageable);
    Page<Cuve> getAllCuveByKeyword(String keyword, Pageable pageable);
    Cuve getCuveById(int id);

    void deleteCuve(int id);
    Cuve getCuveByName(String cuveName);
 
}
