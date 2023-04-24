package sn.sastrans.backofficev2.traceTest.evenementTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sn.sastrans.backofficev2.security.models.UserPrincipal;
import sn.sastrans.backofficev2.trace.dto.EvenementDto;
import sn.sastrans.backofficev2.trace.models.Evenement;


import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class EvenementControllerIT {

    private static  final String END_POINT_PATH="/trace/evenements";
    private static final String jwtSecret = "bezKoderSecretKey";
//    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWJhY2FyLmRpZW5nNDFAZ21haWwuY29tIiwiaWF0IjoxNjgyMDk4NTMzLCJleHAiOjE2ODIxODQ5MzN9.7bjT3_6nC9CcmsdgnH0tMIEQ97Deyd9RdMo-1237tk0S3oHf5ngmlvg449PWV5HlQtuh6gseQxjfAA6Kvj1ypA";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public  void testGetEvenementPagination() throws Exception {
        //when
        mockMvc.perform(get(END_POINT_PATH)
                        .param("title","")
                        .param("page","0")
                        .param("size","5")
//                        .contentType(MediaType.APPLICATION_JSON)
                       .header("Authorization", "Bearer " + createToken("babacar.dieng41@gmail.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.evenements.size()").value(1))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andDo(print());

    }
    @Test
    public  void testCreateEvenementShouldReturn201Created() throws Exception {
        //given
        EvenementDto eventDto = new EvenementDto();
        eventDto.setDateEvent(Calendar.getInstance().getTime());
        eventDto.setNatureEvent("PANNE");
        eventDto.setCauseEvent("MOTEUR");
        eventDto.setMatVehicule("AA 635 BL");
        eventDto.setTypeVehicule("C2");
        eventDto.setPkEvent("A2 36+300");
        eventDto.setSens("SENS1");
        eventDto.setVoie("VL");
        eventDto.setSecteur("TT");
        eventDto.setEmisPar("PAT");
        eventDto.setHeureDebutEvent("08:30");
        eventDto.setHeureAppelOPC("08:32");
        eventDto.setNomPAT("Aly KA");
        eventDto.setStatutEvent("assiste et reparti seul");
        String requestBody = objectMapper.writeValueAsString(eventDto);

        //when
        mockMvc.perform(post(END_POINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + createToken("babacar.dieng41@gmail.com"))
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",is("/trace/evenement/edit/9")))
                .andDo(print());

    }
    private String createToken(String username) {

//        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000L); // 1 hour
        return Jwts.builder()
//                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
