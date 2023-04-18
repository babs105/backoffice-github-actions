package sn.sastrans.backofficev2.traceTest.evenementTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.repositories.EvenementRepository;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EvenementRepositoryTest {

    @Autowired
    private EvenementRepository evenementRepository;


    @Test
    @WithMockUser(username = "babacar.dieng41@gmail.com")
    public void saveAndGetEvenementOk(){
       //given
       Evenement event = Evenement.builder()
               .dateEvent(Calendar.getInstance().getTime())
               .natureEvent("PANNE")
               .causeEvent("MOTEUR")
               .matVehicule("AA 635 BL")
               .typeVehicule("C2")
               .pkEvent("A2 36+300")
               .sens("SENS1")
               .voie("VL")
               .secteur("TT")
               .emisPar("PAT")
               .heureDebutEvent("08:30")
               .heureAppelOPC("08:32")
               .nomPAT("Aly KA")
               .statutEvent("assiste et reparti seul")
               .build();

       //when
        Evenement eventSaved = evenementRepository.save(event);

        //then
//        verify(evenementRepository).save(event);

        assertAll(() -> {
                    assertNotNull(eventSaved.getId());
                    assertEquals(event.getNatureEvent(),eventSaved.getNatureEvent());
                    assertEquals("MOTEUR",eventSaved.getCauseEvent());
                });

        //when
        Evenement retrieveEvent = evenementRepository.findById(eventSaved.getId()).orElse(null);

        //then
//        verify(evenementRepository).findById(eventSaved.getId());
        //then
        assertEquals(retrieveEvent,eventSaved);
    }
}
