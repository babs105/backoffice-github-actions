package sn.sastrans.backofficev2.trace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.trace.dto.EventSearchDTO;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.services.DetailAccidentService;
import sn.sastrans.backofficev2.trace.services.EvenementService;
import sn.sastrans.backofficev2.trace.services.RemorquageService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@Controller
public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    @Autowired
    private DetailAccidentService detailAccidentService;

    @Autowired
    private RemorquageService remorquageService;


    @GetMapping("/trace/evenements")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllEvenements(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<Evenement> evenements = new ArrayList<Evenement>();
            Pageable paging = PageRequest.of(page, size, Sort.by("dateEvent").descending());

            Page<Evenement> pageEvents;
            if (title == null)
                pageEvents = evenementService.getAllEvenement(paging);
            else
                pageEvents = evenementService.getAllEvenementByKeyword(title, paging);

            evenements = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("evenements", evenements);
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // show form add
    @GetMapping("/trace/evenementAdd")
    public String showFormAddEvenement(Model model){
        return "trace/evenementAdd";
    }

    @PostMapping("/trace/search")
    @ResponseBody
    public ResponseEntity<List<Evenement>> searchEvenement(@RequestBody EventSearchDTO critere){
        try{
            return new ResponseEntity<>(evenementService.searchEvenement(critere), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Add Evenement
    @PostMapping("/trace/evenements")
    public String addEvenement(@Valid Evenement evenement, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "trace/evenementAdd";
        }
         Evenement evenementSaved = evenementService.saveEvenement(evenement);
         if(evenementSaved != null){
             redirAttrs.addFlashAttribute("success", "Evenement cree avec Succes.");
         }else{
             redirAttrs.addFlashAttribute("error", "Erreur creation Evenement.");
         }
        return "redirect:/trace/evenements";
    }
    @PostMapping("/trace/update/evenement")
    public String updateEvenement(@Valid Evenement evenement, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "trace/evenementEdit";
        }
        Evenement evenementSaved = evenementService.saveEvenement(evenement);
        if(evenementSaved != null){
            redirAttrs.addFlashAttribute("success", "Evenement Modifie avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur modification Evenement.");
        }
//        return "redirect:/trace/evenements";
        return "redirect:/trace/evenement/Edit/"+evenement.getId();
    }


    //The op parameter is either Edit or Details show form
//    @GetMapping("/trace/evenement/{op}/{id}")
//    public String editEvenement(@PathVariable Integer id, @PathVariable String op, Model model){
//        Evenement evenement = evenementService.getEvenementById(id);
////       log.info("evenemt" +evenement);
//        model.addAttribute("evenement", evenement);
//        model.addAttribute("remorquages",evenement.getRemorquages());
//        model.addAttribute("accidents",detailAccidentService.getDetailAccidentByEventId(id));
//        model.addAttribute("sizeAccident",detailAccidentService.getDetailAccidentByEventId(id).size());
////        addModelAttributes(model);
//        return "trace/evenement"+op; //returns EvenementEdit or EvenementDetails
//    }
    @GetMapping("/trace/evenements/{id}")
    @ResponseBody
    public ResponseEntity<Evenement> editEvenement(@PathVariable Integer id){
        Evenement evenement = evenementService.getEvenementById(id);

        if(evenement!=null){
            return new ResponseEntity<>(evenement, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
// delete Evenement by id
    @DeleteMapping("/trace/evenements/delete/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteEvenement(@PathVariable Integer id){
        try {
            evenementService.deleteEvenement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trace/evenement/{eventId}/deleteEvent/{romId}")
    public String deleteRomInEvenement(@PathVariable Integer eventId,@PathVariable Integer romId){
        remorquageService.deleteRemorquage(romId);
        return "redirect:/trace/evenement/Edit/"+eventId;
    }
    @GetMapping("/trace/evenement/{eventId}/deleteAccident/{accidentId}")
    public String deleteAcciInEvenement(@PathVariable Integer eventId,@PathVariable Integer accidentId){
        detailAccidentService.deleteDetailAccident(accidentId);
        return "redirect:/trace/evenement/Edit/"+eventId;
    }
}
