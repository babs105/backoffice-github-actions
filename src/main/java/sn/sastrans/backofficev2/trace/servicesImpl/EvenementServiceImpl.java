package sn.sastrans.backofficev2.trace.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.EventSearchDTO;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.repositories.EvenementRepository;
import sn.sastrans.backofficev2.trace.services.EvenementService;

import java.util.List;
@Service
public class EvenementServiceImpl  implements EvenementService {

    @Autowired
    EvenementRepository evenementRepository;
    @Override
    public Evenement saveEvenement(Evenement evenement) {
        if((evenement.getDateheurePoseBalise()!=null) && (evenement.getDateheureDeposeBalise() == null )){
            if(evenement.getStatutEvent().equalsIgnoreCase("assiste et reparti seul")
                    ||evenement.getStatutEvent().equalsIgnoreCase("remorque")
                    ||evenement.getStatutEvent().equalsIgnoreCase("annule")){
                            evenement.setEtatEvent("Terminer-A-debaliser");
            }
            if(evenement.getStatutEvent().equalsIgnoreCase("reparti seul")){
                evenement.setEtatEvent("Terminer");
            }
            if(evenement.getStatutEvent().equalsIgnoreCase("asuivre")){
                evenement.setEtatEvent("En Cours");
            }

        }
        else if((evenement.getDateheurePoseBalise()!=null) && (evenement.getDateheureDeposeBalise() != null )){
            if(evenement.getStatutEvent().equalsIgnoreCase("assiste et reparti seul")
                    ||evenement.getStatutEvent().equalsIgnoreCase("remorque")
                    ||evenement.getStatutEvent().equalsIgnoreCase("annule")){
                evenement.setEtatEvent("Terminer");
            }
        }
        else if(evenement.getStatutEvent().equalsIgnoreCase("reparti seul")||
                evenement.getStatutEvent().equalsIgnoreCase("assiste et reparti seul")
                ||evenement.getStatutEvent().equalsIgnoreCase("remorque")
                ||evenement.getStatutEvent().equalsIgnoreCase("annule")){
                    evenement.setEtatEvent("Terminer");
        }
     else{
            evenement.setEtatEvent("En Cours");
        }

        return evenementRepository.save(evenement);
    }

    @Override
    public Page<Evenement> getAllEvenement(Pageable pageable) {
        return evenementRepository.findAll(pageable);
    }

    @Override
    public Page<Evenement> getAllEvenementByKeyword(String keyword, Pageable pageable) {
        return evenementRepository.findByKeyword(keyword, pageable);
    }


    @Override
    public Evenement getEvenementById(int id) {
        return evenementRepository.findById(id).orElse(null);
    }

    @Override
    public List<Evenement> searchEvenement(EventSearchDTO critere) {
        return evenementRepository.searchEvenement(critere);
    }

    @Override
    public void deleteEvenement(int id) {
        evenementRepository.deleteById(id);

    }
}
