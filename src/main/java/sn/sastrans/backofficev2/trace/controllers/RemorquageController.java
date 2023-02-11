package sn.sastrans.backofficev2.trace.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.models.Remorquage;
import sn.sastrans.backofficev2.trace.services.EvenementService;
import sn.sastrans.backofficev2.trace.services.RemorquageService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class RemorquageController {

    @Autowired
    private RemorquageService remorquageService;
    @Autowired
    private EvenementService evenementService;


    public Model addModelAttributes(Model model){

        model.addAttribute("remorquage", new Remorquage());
        return model;
    }

   //get ALL page
    @GetMapping("/trace/remorquages")
//    public String getAllPages(Model model, String keyword, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  dateFin)  {
    public String getAllPages(Model model, String keyword,@DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateDebut,@DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateFin)  {
        log.info("keyword "+keyword);
        log.info("dateDebut "+dateDebut);
        log.info("dateFin "+dateFin);
//      if(keyword==null){
//         keyword="" ;
//      }
        return getPageWithSort(model,1,keyword,dateDebut,dateFin,"r.dateRom","desc");
//        return "";
    }

    @GetMapping("/trace/remorquages/page/{pageNumber}")
    public String getPageWithSort(Model model,
                                               @PathVariable("pageNumber") int currentPage,
                                               @PathParam("keyword") String keyword,
                                               @PathParam("dateDebut") @DateTimeFormat(pattern= "yyyy-MM-dd") Date dateDebut,
                                               @PathParam("dateFin") @DateTimeFormat(pattern= "yyyy-MM-dd") Date dateFin,
                                               @PathParam("sortField") String sortField,
                                               @PathParam("sortDir") String sortDir){

               Page<Remorquage> page= remorquageService.getAllRemorquageWithSort(keyword,dateDebut,dateFin,sortField,sortDir,currentPage);

               int totalPages = page.getTotalPages();
               long totalElements = page.getTotalElements();
               List<Remorquage> remorquages = page.getContent();

               model.addAttribute("currentPage", currentPage);
               model.addAttribute("totalPages",totalPages);
               model.addAttribute("totalElements",totalElements);
               model.addAttribute("remorquages",remorquages);

               model.addAttribute("keyword", keyword);
               model.addAttribute("dateDebut", dateDebut);
               model.addAttribute("dateFin", dateFin);
               model.addAttribute("sortField", sortField);
               model.addAttribute("sortDir", sortDir);
               model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");

        return "trace/remorquages";


    }
    // show form add
    @GetMapping("/trace/remorquageAdd/{id}")
    public String showFormAddRemorquage(@PathVariable Integer id, Model model){

        Evenement event = evenementService.getEvenementById(id);
        model.addAttribute("remorquages",event.getRemorquages());
        model.addAttribute("evenement",event);

     addModelAttributes(model);
        return "trace/remorquageAdd";
    }


    //Add Remorquage
    @PostMapping("/trace/remorquages")
    public String addRemorquage(@Valid Remorquage remorquage, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "trace/remorquageEdit";
        }
         Remorquage remorquageSaved = remorquageService.saveRemorquage(remorquage);
         if(remorquageSaved != null){
             Evenement evenement = evenementService.getEvenementById(remorquage.getEventid());
             evenement.setStatutEvent(remorquage.getStatutRom());
             evenement.setStatutRomEvent(remorquage.getStatutRom());
             evenementService.saveEvenement(evenement);
             redirAttrs.addFlashAttribute("success", "Remorquage ajout avec Succes.");

         }else{
             redirAttrs.addFlashAttribute("error", "Erreur ajout Remorquage.");
         }

        return "redirect:/trace/evenement/Edit/"+remorquage.getEventid();
    }

    //Add Remorquage
    @PostMapping("/trace/update/remorquages")
    public String updateRemorquage(@Valid Remorquage remorquage, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "trace/remorquageEdit";
        }
        Remorquage remorquageSaved = remorquageService.saveRemorquage(remorquage);
        if(remorquageSaved != null){
            Evenement evenement = evenementService.getEvenementById(remorquage.getEventid());
            evenement.setStatutEvent(remorquage.getStatutRom());
            evenement.setStatutRomEvent(remorquage.getStatutRom());
            evenementService.saveEvenement(evenement);
            redirAttrs.addFlashAttribute("success", "Remorquage Modifie avec Succes.");

        }else{
            redirAttrs.addFlashAttribute("error", "Erreur Modification Remorquage.");
        }

        return "redirect:/trace/evenement/Edit/"+remorquage.getEventid();
    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/trace/remorquage/{op}/{id}")
    public String editRemorquage(@PathVariable Integer id, @PathVariable String op, Model model){
        Remorquage remorquage = remorquageService.getRemorquageById(id);
        model.addAttribute("remorquage",remorquage);
//        addModelAttributes(model);
        return "trace/remorquage"+op; //returns RemorquageEdit or RemorquageDetails
    }
// delete Remorquage by id
    @GetMapping("/trace/remorquage/delete/{id}")
    public String deleteRemorquage(@PathVariable Integer id){
        remorquageService.deleteRemorquage(id);
        return "redirect:/trace/remorquages";
    }





}
