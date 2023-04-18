package sn.sastrans.backofficev2.trace.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.trace.dto.EventSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchRequestDto;
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
            else if(evenement.getStatutEvent().equalsIgnoreCase("reparti seul")){
                evenement.setEtatEvent("Terminer");
            }
            else if(evenement.getStatutEvent().equalsIgnoreCase("asuivre")){
                evenement.setEtatEvent("En Cours");
            }

        }
        else if((evenement.getDateheurePoseBalise()!=null) && (evenement.getDateheureDeposeBalise() != null )){
            if(evenement.getStatutEvent().equalsIgnoreCase("assiste et reparti seul")
                    ||evenement.getStatutEvent().equalsIgnoreCase("remorque")
                    ||evenement.getStatutEvent().equalsIgnoreCase("annule")||
                    evenement.getStatutEvent().equalsIgnoreCase("reparti seul")
            ){
                evenement.setEtatEvent("Terminer");
            }else{
                evenement.setEtatEvent("En Cours");
            }
        }
        else if(evenement.getStatutEvent().equalsIgnoreCase("reparti seul")||
                evenement.getStatutEvent().equalsIgnoreCase("assiste et reparti seul")
                ||evenement.getStatutEvent().equalsIgnoreCase("remorque")
                ||evenement.getStatutEvent().equalsIgnoreCase("annule")){
                    evenement.setEtatEvent("Terminer");
        }else{
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
    public Evenement getEvenementById(Integer id) {

        return evenementRepository.findById(id).get();
    }

    @Override
    public Page<Evenement> searchEvenement(EventSearchRequestDto critere,Pageable paging ){
        return evenementRepository.searchEvenement(critere,paging);

    }
    public Evenement updateEvenement(Integer id, Evenement event) {

        Evenement eventSaved = evenementRepository.findById(id).get();
          if(eventSaved!=null){
              event.setId(id);

          }

      return   saveEvenement(event);


    }

    @Override
    public List<Evenement> searchEvenementExcel(EventSearchExcelRequestDto critere) {
        return evenementRepository.searchEvenementExcel(critere);
    }

    @Override
    public Evenement deleteEvenement(Integer id) {
     Evenement event = evenementRepository.findById(id).get();
     if(event!=null) {
         evenementRepository.deleteById(id);
         return event;
     }
     return null;

    }
}
