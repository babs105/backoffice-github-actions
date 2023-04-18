package sn.sastrans.backofficev2.traceTest.evenementTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sn.sastrans.backofficev2.trace.controllers.EvenementController;
import sn.sastrans.backofficev2.trace.dto.EvenementDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchExcelRequestDto;
import sn.sastrans.backofficev2.trace.dto.EventSearchRequestDto;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapper;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.services.EvenementService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat ;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
public class EvenementControllerTest {

    private static  final String END_POINT_PATH="/trace/evenements";

    private MockMvc mockMvc;

    @Autowired
    private EvenementController evenementController;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private EvenementService evenementService;

    @MockBean
    private EvenementMapper evenementMapper;

   ///@Autowired private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(evenementController).build();
    }
//    @BeforeAll
//    public static void setUp() {
//        // Créer un objet UserDetails simulé avec le nom d'utilisateur "john"
////        userDetailsService = new UserDetailsServiceImpl();
//
//    }

    @Test
    public  void testGetEvenementPagination() throws Exception {


        //given
        String title="";
        Integer pageNumber = 0;
        Integer size = 5;
        Pageable paging = PageRequest.of(pageNumber, size, Sort.by("dateEvent", "heureDebutEvent").descending());
        List<Evenement> eventList = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
           Evenement event = new Evenement();
            event.setId(i);
            event.setNatureEvent("Panne :" +i);
            event.setDateEvent(Calendar.getInstance().getTime());
            event.setHeureDebutEvent("08:0"+i);
            eventList.add(event);
        }
        int totalevent=  eventList.size();
        List<Evenement> eventSousList = eventList.subList(
                (int) paging.getOffset(),
                (int) Math.min(paging.getOffset()+paging.getPageSize(),totalevent));

        if(title.length()>0) {
            eventSousList = eventSousList.stream().filter(event -> event.getNatureEvent().contains(title)).collect(Collectors.toList());
        }

        Page<Evenement> eventPage = new PageImpl<>(eventSousList,paging,totalevent);

       if(title.length()>0){
           when(evenementService.getAllEvenementByKeyword(title,paging)).thenReturn(eventPage);
       }else{
           when(evenementService.getAllEvenement(paging)).thenReturn(eventPage) ;
       }

        // Mock the DTOs
        List<EvenementDto> eventDtoList = new ArrayList<>();
                for (int i = 1; i < 16; i++) {
           EvenementDto eventDto = new EvenementDto();
                    eventDto.setId(i);
                    eventDto.setNatureEvent("Panne :" +i);
                    eventDto.setDateEvent(Calendar.getInstance().getTime());
                    eventDto.setHeureDebutEvent("08:0"+i);
                    eventDtoList.add(eventDto);
        }
        int totaleventDto =  eventDtoList.size();
        List<EvenementDto> eventDtoSousList = eventDtoList.subList(
                (int) paging.getOffset(),
                (int) Math.min(paging.getOffset()+paging.getPageSize(),totaleventDto));

        if(title.length()>0) {
            eventDtoSousList =  eventDtoSousList.stream().filter(event -> event.getNatureEvent().contains(title)).collect(Collectors.toList());
        }
        Page<EvenementDto> eventDtoPage = new PageImpl<>(eventDtoSousList,paging,totaleventDto);
        when(evenementMapper.toDto(eventPage.getContent())).thenReturn(eventDtoPage.getContent());

        //when
        mockMvc.perform(get(END_POINT_PATH)
                        .param("title",title)
                        .param("page","0")
                        .param("size","5"))
//                        .contentType(MediaType.APPLICATION_JSON)
//
//                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.totalItems").value(15))
                        .andExpect(jsonPath("$.evenements.size()").value(5))
                        .andExpect(jsonPath("$.pageSize").value(5))
                        .andExpect(jsonPath("$.currentPage").value(0))
                        .andDo(print());

    }

    @Test
    public  void testGetEventSearchPaginationShouldReturnOK() throws Exception {
        //given

        Integer pageNumber = 0;
        Integer size = 5;
        Pageable paging = PageRequest.of(pageNumber, size);

        EventSearchRequestDto searchRequestDto =  EventSearchRequestDto.builder()
                .page(pageNumber)
                .size(size)
                .secteur("TT")
                .natureEvent("Panne")
                .build();

        List<Evenement> eventList = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            Evenement event = new Evenement();
            event.setId(i);
            event.setSecteur("TT");
            event.setNatureEvent("Panne :" +i);
            event.setDateEvent(Calendar.getInstance().getTime());
            event.setHeureDebutEvent("08:0"+i);
            eventList.add(event);
        }
        int totalevent=  eventList.size();
        List<Evenement> eventSousList = eventList.subList(
                (int) paging.getOffset(),
                (int) Math.min(paging.getOffset()+paging.getPageSize(),totalevent));

        Page<Evenement> eventPage = new PageImpl<>(eventSousList,paging,totalevent);
        when(evenementService.searchEvenement(searchRequestDto,paging)).thenReturn(eventPage) ;

        // Mock the DTOs
        List<EvenementDto> eventDtoList = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            EvenementDto eventDto = new EvenementDto();
            eventDto.setId(i);
            eventDto.setSecteur("TT");
            eventDto.setNatureEvent("Panne :" +i);
            eventDto.setDateEvent(Calendar.getInstance().getTime());
            eventDto.setHeureDebutEvent("08:0"+i);
            eventDtoList.add(eventDto);
        }
        int totaleventDto =  eventDtoList.size();
        List<EvenementDto> eventDtoSousList = eventDtoList.subList(
                (int) paging.getOffset(),
                (int) Math.min(paging.getOffset()+paging.getPageSize(),totaleventDto));


        Page<EvenementDto> eventDtoPage = new PageImpl<>(eventDtoSousList,paging,totaleventDto);
        when(evenementMapper.toDto(eventPage.getContent())).thenReturn(eventDtoPage.getContent());
        String requestBody = objectMapper.writeValueAsString(searchRequestDto);

        //when
        mockMvc.perform(post(END_POINT_PATH+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(15))
                .andExpect(jsonPath("$.evenements.size()").value(5))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andDo(print());

    }

    @Test
    public  void testCreateEvenementShouldReturn201Created() throws Exception {
        //given

        Evenement event= new Evenement();
        event.setId(1);
        event.setDateEvent(Calendar.getInstance().getTime());
        event.setNatureEvent("PANNE");
        event.setCauseEvent("MOTEUR");
        event.setMatVehicule("AA 635 BL");
        event.setTypeVehicule("C2");
        event.setPkEvent("A2 36+300");
        event.setSens("SENS1");
        event.setVoie("VL");
        event.setSecteur("TT");
        event.setEmisPar("PAT");
        event.setHeureDebutEvent("08:30");
        event.setHeureAppelOPC("08:32");
        event.setNomPAT("Aly KA");
        event.setStatutEvent("assiste et reparti seul");

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
        when(evenementMapper.toDto(event)).thenReturn(eventDto);
        when(evenementMapper.toEntity(eventDto)).thenReturn(event);
        when(evenementService.saveEvenement(any())).thenReturn(event);

        String requestBody = objectMapper.writeValueAsString(eventDto);

        //when
        mockMvc.perform(post(END_POINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",is("/trace/evenement/edit/1")))
                .andDo(print());

    }

    @Test
    public  void testCreateEvenementShouldReturn400BadRequest() throws Exception {
        //given
        EvenementDto event = new EvenementDto();
        event.setDateEvent( Calendar.getInstance().getTime());
        event.setNatureEvent("PANNE");
        event.setCauseEvent("MOTEUR");


        String requestBody = objectMapper.writeValueAsString(event);

        //when
        mockMvc.perform(post(END_POINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
                        .content(requestBody))
                        .andExpect(status().isBadRequest())
                        .andDo(print());

    }

    @Test
    public  void testGetEvenementWithInValidIdShouldReturn404NotFound() throws Exception {

        //given
        Integer invalidId = 400;
        when(evenementService.getEvenementById(invalidId)).thenThrow(new NoSuchElementException());

        //when
//        MvcResult mvcResult= mockMvc.perform(get(END_POINT_PATH+"/"+invalidId)
//                        .contentType(MediaType.APPLICATION_JSON))
////                        .header("Authorization", "Bearer " + token))
//                        .andExpect(status().isNotFound())
//                        .andDo(print())
//                        .andReturn();
//
//        //then
//       String responseBody = mvcResult.getResponse().getContentAsString();
////        assertThat(responseBody,equalTo("Element introuvable"));
//        Exception exception = mvcResult.getResolvedException();
//        assertThat(exception,instanceOf(NoSuchElementException.class));
//        assertThat(responseBody,equalTo("Element introuvable"));
        Assertions.assertThatThrownBy(
                () -> mockMvc.perform(get(END_POINT_PATH+"/"+invalidId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(result ->  assertThat(result.getResolvedException(),instanceOf(NoSuchElementException.class)))
                        .andExpect(result ->  assertThat(result.getResponse(),equalTo("Element introuvable")))
                        .andDo(print())
        );

        verify(evenementService,times(1)).getEvenementById(invalidId);

    }

    @Test
    public  void testGetEvenementWithValidIdShouldReturn200ok() throws Exception {
        Integer validId = 4;
        Evenement event = new Evenement();
        event.setId(validId);
        event.setNatureEvent("PANNETEST");
        event.setHeureDebutEvent("08:05");

        EvenementDto eventDto = new EvenementDto();
        eventDto.setId(validId);
        eventDto.setNatureEvent("PANNETEST");
        eventDto.setHeureDebutEvent("08:05");

        when(evenementMapper.toDto(event)).thenReturn(eventDto);
        when(evenementService.getEvenementById(validId)).thenReturn(event);


         mockMvc.perform(get(END_POINT_PATH+"/"+validId)
                        .contentType(MediaType.APPLICATION_JSON))
//                         .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", is(4)))
                        .andExpect(jsonPath("$.natureEvent", is("PANNETEST")))
                        .andExpect(jsonPath("$.heureDebutEvent", is("08:05")))
                        .andDo(print());



    }

    @Test
    public void testDeleteEvenementWithValidIdShouldReturn204NoContent() throws Exception {
        Integer validId = 1;
        Evenement event = new Evenement();
        event.setId(validId);
        event.setNatureEvent("PANNETEST");
        event.setHeureDebutEvent("08:05");
        when(evenementService.deleteEvenement(validId)).thenReturn(event);

        mockMvc.perform(delete(END_POINT_PATH+"/delete/"+validId)
                .contentType(MediaType.APPLICATION_JSON))
//                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(evenementService, times(1)).deleteEvenement(validId);
    }

    @Test
    public void testDeleteEvenementWithInValidIdShouldReturn404NotFound() throws Exception {
        Integer invalidId = 400;
        when(evenementService.deleteEvenement(invalidId)).thenThrow(new NoSuchElementException("Element introuvable "));

//     MvcResult  mvcResult=mockMvc.perform(delete(END_POINT_PATH+"/delete/"+invalidId)
//                .contentType(MediaType.APPLICATION_JSON))
////                     .header("Authorization", "Bearer " + token))
//                .andExpect(status().isNotFound())
//                .andDo(print())
//                .andReturn();
//          String responseBody = mvcResult.getResponse().getContentAsString();
////        assertThat(responseBody).isEqualTo("Element introuvable");
//
//        Exception exception = mvcResult.getResolvedException();
//        assertThat(exception,instanceOf(NoSuchElementException.class));
//        assertThat(responseBody,equalTo("Element introuvable"));
        Assertions.assertThatThrownBy(
                () -> mockMvc.perform(delete(END_POINT_PATH+"/delete/"+invalidId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(result ->  assertThat(result.getResolvedException(),instanceOf(NoSuchElementException.class)))
                        .andExpect(result ->  assertThat(result.getResponse(),equalTo("Element introuvable")))
                        .andDo(print())
        );
        verify(evenementService,times(1)).deleteEvenement(invalidId);
    }

    @Test
    public void testUpadateEvenementWithValidIdShouldReturn201Created() throws Exception {
        Integer validId = 1;
        Evenement event= new Evenement();
        event.setId(validId);
        event.setNatureEvent("PANNE");
        event.setCauseEvent("MOTEUR");
        event.setMatVehicule("AA 635 BL");

        EvenementDto eventDto = new EvenementDto();
        eventDto.setId(validId);
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
//        doReturn(event).when(evenementMapper).toEntity(eventDto);
//        doReturn(eventDto).when(evenementMapper).toDto(event);
        when(evenementMapper.toDto(event)).thenReturn(eventDto);
        when(evenementMapper.toEntity(eventDto)).thenReturn(event);
        when(evenementService.updateEvenement(validId,event)).thenReturn(event);

        String requestBody = objectMapper.writeValueAsString(eventDto);
        mockMvc.perform(put(END_POINT_PATH+"/update/"+validId)
                        .contentType(MediaType.APPLICATION_JSON)
                         .content(requestBody))
//                         .header("Authorization", "Bearer " + token))
                         .andExpect(status().isCreated())
                         .andExpect(jsonPath("$.id", is(validId)))
                         .andExpect(jsonPath("$.natureEvent", is(eventDto.getNatureEvent())))
                          .andExpect(jsonPath("$.matVehicule", is(eventDto.getMatVehicule())))
                          .andDo(print());

        verify(evenementService, times(1)).updateEvenement(validId,event);



    }

    @Test
    public void testUpadateEvenementWithInValidIdShouldReturn404NotFound() throws Exception {
        Integer invalidId = 400;
        Evenement event= new Evenement();
        event.setId(invalidId);
        event.setNatureEvent("PANNE");
        event.setCauseEvent("MOTEUR");
        event.setMatVehicule("AA 635 BL");

        EvenementDto eventDto = new EvenementDto();
        eventDto.setId(invalidId);
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
//        doReturn(event).when(evenementMapper).toEntity(eventDto);
//        doReturn(eventDto).when(evenementMapper).toDto(event);
        when(evenementMapper.toDto(event)).thenReturn(eventDto);
        when(evenementMapper.toEntity(eventDto)).thenReturn(event);
        when(evenementService.updateEvenement(invalidId,event)).thenThrow(new NoSuchElementException());

        String requestBody = objectMapper.writeValueAsString(eventDto);
//        MvcResult mvcResult=mockMvc.perform(put(END_POINT_PATH+"/update/"+invalidId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
////                        .header("Authorization", "Bearer " + token))
//                        .andExpect(status().isNotFound())
//
//                        .andDo(print());
//               .andReturn();
//        String responseBody = mvcResult.getResponse().getContentAsString();
//
//        Exception exception = mvcResult.getResolvedException();
//        assertThat(exception,instanceOf(NoSuchElementException.class));
//        assertThat(responseBody,equalTo("Element introuvable"));

        Assertions.assertThatThrownBy(
                        () -> mockMvc.perform(put(END_POINT_PATH+"/update/"+invalidId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isNotFound())
                                .andExpect(result ->  assertThat(result.getResolvedException(),instanceOf(NoSuchElementException.class)))
                                .andExpect(result ->  assertThat(result.getResponse(),equalTo("Element introuvable")))
                                .andDo(print())
        );
        verify(evenementService, times(1)).updateEvenement(invalidId,event);







    }

    @Test
    public void testDownloadExcel() throws Exception {
        EventSearchExcelRequestDto searchRequestExcelDto = new EventSearchExcelRequestDto();

        searchRequestExcelDto.setSecteur("TT");
        searchRequestExcelDto.setNatureEvent("Panne");


        List<Evenement> eventList = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            Evenement event = new Evenement();
            event.setId(i);
            event.setSecteur("TT");
            event.setNatureEvent("Panne :" +i);
            event.setDateEvent(Calendar.getInstance().getTime());
            event.setHeureDebutEvent("08:0"+i);
            eventList.add(event);
        }
        // Mock the DTOs
        List<EvenementDto> eventDtoList = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            EvenementDto eventDto = new EvenementDto();
            eventDto.setId(i);
            eventDto.setSecteur("TT");
            eventDto.setNatureEvent("Panne :" +i);
            eventDto.setDateEvent(Calendar.getInstance().getTime());
            eventDto.setHeureDebutEvent("08:0"+i);
            eventDtoList.add(eventDto);
        }
        when(evenementMapper.toDto(eventList)).thenReturn(eventDtoList);
        when(evenementService.searchEvenementExcel(searchRequestExcelDto)).thenReturn(eventList);

        String requestBody = objectMapper.writeValueAsString(searchRequestExcelDto);

        MvcResult mvcResult = mockMvc.perform(post(END_POINT_PATH+"/search/excel")
                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer " + token)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andReturn();

        // Vérification du contenu de la réponse
        byte[] responseContent = mvcResult.getResponse().getContentAsByteArray();
        InputStream inputStream = new ByteArrayInputStream(responseContent);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        assertEquals("Evenement ID", cell.getStringCellValue());
        workbook.close();

}

//private String getAccessToken() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(post("/public/user/signin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(new LoginRequestDto("babacar.dieng41@gmail.com", "passer2020"))))
//                        .andExpect(status().isOk())
//                        .andReturn();
//                  String token = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JwtResponseDto.class).getAccessToken();
//
//                  return token;
//    }

}
