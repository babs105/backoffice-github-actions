package sn.sastrans.backofficev2.traceTest.evenementTest;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.repositories.EvenementRepository;
import sn.sastrans.backofficev2.trace.services.EvenementService;
import sn.sastrans.backofficev2.trace.servicesImpl.EvenementServiceImpl;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EvenementServiceTest {

    @InjectMocks
    private static  EvenementService  evenementService ;
//            = new EvenementServiceImpl();

    @Mock
    private EvenementRepository evenementRepository;


    @BeforeAll
    public static void setUp() {
        evenementService = new EvenementServiceImpl();
    }

    @Test
    public  void testGetEvenement(){
  // given
     Optional<Evenement> event = Optional.of(Evenement.builder()
                .id(1)
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
                .build());
        Mockito.when(evenementRepository.findById(1)).thenReturn(event);

        // when
        Evenement evenement = evenementService.getEvenementById(1);

         //then
        verify(evenementRepository).findById(1);
        assertAll(
                () -> {
                    assertEquals(event.get().getNatureEvent(),evenement.getNatureEvent());
                    assertEquals(event.get().getCauseEvent(),evenement.getCauseEvent());
                }
        );
    }



}
