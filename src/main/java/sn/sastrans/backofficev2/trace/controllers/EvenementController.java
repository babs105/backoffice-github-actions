package sn.sastrans.backofficev2.trace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.sastrans.backofficev2.trace.dto.*;
import sn.sastrans.backofficev2.trace.exportfile.EventSearchExcelExporter;
import sn.sastrans.backofficev2.trace.mappers.EvenementMapper;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.services.DetailAccidentService;
import sn.sastrans.backofficev2.trace.services.EvenementService;
import sn.sastrans.backofficev2.trace.services.RemorquageService;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController

public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    @Autowired
    private DetailAccidentService detailAccidentService;

    @Autowired
    private RemorquageService remorquageService;

    @Autowired
    EvenementMapper evenementMapper;


    @GetMapping("/trace/evenements")
    public ResponseEntity<Map<String, Object>> getAllEvenements(@RequestParam(required = false,defaultValue = "") String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
       log.info("title :"+title);
        Pageable paging = PageRequest.of(page, size, Sort.by("dateEvent", "heureDebutEvent").descending());
        List<EvenementDto> evenementDtos= new ArrayList<>();
        Page<Evenement> pageEvents;
//        !title.isEmpty() && title!=null
        if (title.length()>0) {
            pageEvents = evenementService.getAllEvenementByKeyword(title,paging);
        }else{
            pageEvents = evenementService.getAllEvenement(paging);
        }

         evenementDtos = evenementMapper.toDto(pageEvents.getContent());


        Map<String, Object> response = new HashMap<>();
        response.put("evenements", evenementDtos);
        response.put("currentPage", pageEvents.getNumber());
        response.put("totalItems", pageEvents.getTotalElements());
        response.put("pageSize", pageEvents.getPageable().getPageSize());
        response.put("totalPages", pageEvents.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // show form add
//    @GetMapping("/trace/evenementAdd")
//    public String showFormAddEvenement(Model model){
//        return "trace/evenementAdd";
//    }

    @PostMapping("/trace/evenements/search")
    public ResponseEntity<Map<String,Object>> searchEvenement(@RequestBody EventSearchRequestDto critere) {

            Pageable paging = PageRequest.of(critere.getPage(), critere.getSize());
            Page<Evenement> pageEventsSerch= evenementService.searchEvenement(critere,paging);

            List<EvenementDto> evenementDtos = evenementMapper.toDto(pageEventsSerch.getContent());

            Map<String, Object> response = new HashMap<>();
            response.put("evenements", evenementDtos);
            response.put("currentPage", pageEventsSerch.getNumber());
            response.put("totalItems", pageEventsSerch.getTotalElements());
            response.put("pageSize", pageEventsSerch.getPageable().getPageSize());
            response.put("totalPages", pageEventsSerch.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
//

    }

    //    Add Evenement
    @PostMapping("/trace/evenements")
    public ResponseEntity<?> addEvenement( @Valid @RequestBody EvenementDto evenementDto) {


              Evenement event = evenementService.saveEvenement(evenementMapper.toEntity(evenementDto));
              URI uri = URI.create("/trace/evenement/edit/" +event.getId());
              return ResponseEntity.created(uri).body(evenementMapper.toDto(event));
    }

    @PutMapping("/trace/evenements/update/{id}")
    public ResponseEntity<?> updateEvenement(@PathVariable Integer id, @RequestBody @Valid EvenementDto evenementDto) {
//        log.info("DED" + evenementDto.getId());

        Evenement event = evenementMapper.toEntity(evenementDto);
        EvenementDto eventDto = evenementMapper.toDto(evenementService.updateEvenement(id,event));

            return new ResponseEntity<EvenementDto>(eventDto, HttpStatus.CREATED);

    }

    @GetMapping("/trace/evenements/{id}")
    public ResponseEntity<?> editEvenement(@PathVariable Integer id) {

           Evenement event = evenementService.getEvenementById(id);
//           if(event==null) throw new NoSuchElementException();
           EvenementDto evenementdto = evenementMapper.toDto(event);
           return new ResponseEntity<EvenementDto>(evenementdto, HttpStatus.OK);
    }

    // delete Evenement by id
    @DeleteMapping("/trace/evenements/delete/{id}")
    public ResponseEntity<?> deleteEvenement(@PathVariable Integer id) {
            evenementService.deleteEvenement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/trace/evenements/search/excel")
    public void  searchEvenementExcel(@RequestBody @Valid EventSearchExcelRequestDto critre , HttpServletResponse response) throws IOException {

        try {
            List<EvenementDto> evenementsdto = evenementMapper.toDto(evenementService.searchEvenementExcel(critre));
            if(evenementsdto!=null){

                EventSearchExcelExporter excelExporter = new EventSearchExcelExporter(evenementsdto);
                response.setContentType("application/octet-stream");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=evenements" + currentDateTime + ".xlsx";
                response.setHeader(headerKey, headerValue);

                excelExporter.export(response);
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Créer un nouveau fichier Excel
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Rapport");
//
//        // Ajouter des en-têtes de colonne
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Nom");
//        headerRow.createCell(1).setCellValue("Âge");
//        headerRow.createCell(2).setCellValue("Adresse");
//
//        // Ajouter des données
//        Row dataRow = sheet.createRow(1);
//        dataRow.createCell(0).setCellValue("John Doe");
//        dataRow.createCell(1).setCellValue(30);
//        dataRow.createCell(2).setCellValue("123 Main St, Anytown USA");
//
//        // Écrire les données dans le fichier Excel
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        workbook.write(outputStream);
//
//        // Retourner le fichier Excel en tant que réponse HTTP
//        byte[] bytes = outputStream.toByteArray();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "rapport.xlsx");
//        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);


//


    }

}
