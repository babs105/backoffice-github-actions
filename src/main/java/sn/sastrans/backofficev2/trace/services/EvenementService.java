package sn.sastrans.backofficev2.trace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.trace.dto.EventSearchDTO;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.List;

public interface EvenementService {
    Evenement saveEvenement(Evenement evenement);
    Page<Evenement> getAllEvenement(Pageable pageable);
    Page<Evenement> getAllEvenementByKeyword(String keyword,Pageable pageable);
    Evenement getEvenementById(int id);

    List<Evenement> searchEvenement(EventSearchDTO critere);
    void deleteEvenement(int id);
}
