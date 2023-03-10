package sn.sastrans.backofficev2.trace.repositories;

import sn.sastrans.backofficev2.trace.dto.EventSearchDTO;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.List;


public interface EvenementRepositoryCustom {

    List<Evenement> searchEvenement(EventSearchDTO critere);

}
