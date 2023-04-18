package sn.sastrans.backofficev2.traceTest.mappersTest;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import sn.sastrans.backofficev2.trace.dto.EvenementDto;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapper;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapperImpl;
import sn.sastrans.backofficev2.trace.mappers.RemorquageMapper;
import sn.sastrans.backofficev2.trace.mappers.RemorquageMapperImpl;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class EvenementMapperTest {
  private static EvenementMapper evenementMapper = new EvenementMapperImpl();;
  private static RemorquageMapper remorquageMapper = new RemorquageMapperImpl();


//    @Autowired
//    private EvenementMapper evenementMapper;

    @BeforeAll
    public static void setUp() {
        ReflectionTestUtils.setField(evenementMapper,"remorquageMapper",remorquageMapper);
    }

    @Test
    public void testEvenementMapper(){
        //given
        Evenement event = Evenement.builder()
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
                .build();

        //when
        EvenementDto eventDto = evenementMapper.toDto(event);


        //then
        assertAll(
                () -> {
                    assertEquals(event.getNatureEvent(),eventDto.getNatureEvent());
                    assertEquals(event.getPkEvent(),eventDto.getPkEvent());
                }
        );
    }
}
