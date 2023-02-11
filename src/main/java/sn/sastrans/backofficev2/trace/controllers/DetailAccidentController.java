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
import sn.sastrans.backofficev2.trace.models.DetailAccident;
import sn.sastrans.backofficev2.trace.models.Evenement;
import sn.sastrans.backofficev2.trace.services.DetailAccidentService;
import sn.sastrans.backofficev2.trace.services.EvenementService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class DetailAccidentController {

    @Autowired
    private DetailAccidentService detailAccidentService;
    @Autowired
    private EvenementService evenementService;


    public Model addModelAttributes(Model model){

        model.addAttribute("detailAccident", new DetailAccident());
        return model;
    }

   //get ALL page
    @GetMapping("/trace/detailAccidents")
    public String getAllPages(Model model, String keyword,@DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateDebut,@DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateFin)  {
        log.info("keyword "+keyword);
        log.info("dateDebut "+dateDebut);
        log.info("dateFin "+dateFin);
//      if(keyword==null){
//         keyword="" ;
//      }
       return getPageWithSort(model,1,keyword,dateDebut,dateFin,"e.dateEvent","desc");
    }

    @GetMapping("/trace/detailAccidents/page/{pageNumber}")
    public String getPageWithSort(Model model,
                                  @PathVariable("pageNumber") int currentPage,
                                  @PathParam("keyword") String keyword,
                                  @PathParam("dateDebut") @DateTimeFormat(pattern= "yyyy-MM-dd") Date dateDebut,
                                  @PathParam("dateFin") @DateTimeFormat(pattern= "yyyy-MM-dd") Date dateFin,
                                  @PathParam("sortField") String sortField,
                                  @PathParam("sortDir") String sortDir){

               Page<DetailAccident> page= detailAccidentService.getAllDetailAccidentWithSort(keyword,dateDebut,dateFin,sortField,sortDir,currentPage);

               int totalPages = page.getTotalPages();
               long totalElements = page.getTotalElements();
               List<DetailAccident> detailAccidents = page.getContent();

               model.addAttribute("currentPage", currentPage);
               model.addAttribute("totalPages",totalPages);
               model.addAttribute("totalElements",totalElements);
               model.addAttribute("detailAccidents",detailAccidents);

        model.addAttribute("keyword", keyword);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");

        return "trace/detailAccidents";


    }
    // show form add
    @GetMapping("/trace/detailAccidentAdd/{id}")
    public String showFormAddDetailAccident(@PathVariable Integer id, Model model){

        Evenement event = evenementService.getEvenementById(id);

        model.addAttribute("evenement",event);

     addModelAttributes(model);
        return "trace/detailAccidentAdd";
    }


    //Add DetailAccident
    @PostMapping("/trace/detailAccidents")
    public String addDetailAccident(@Valid DetailAccident detailAccident, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "trace/detailAccidentAdd";
        }
         DetailAccident detailAccidentSaved = detailAccidentService.saveDetailAccident(detailAccident);
         if(detailAccidentSaved != null){

             redirAttrs.addFlashAttribute("success", "DetailAccident ajoute avec Succes.");

         }else{
             redirAttrs.addFlashAttribute("error", "Erreur ajout DetailAccident.");
         }

        return "redirect:/trace/evenement/Edit/"+detailAccident.getEventid();
    }
    //Update DetailAccident
    @PostMapping("/trace/update/detailAccidents")
    public String updateDetailAccident(@Valid DetailAccident detailAccident, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "trace/detailAccidentEdit";
        }
        DetailAccident detailAccidentSaved = detailAccidentService.saveDetailAccident(detailAccident);
        if(detailAccidentSaved != null){
            Evenement evenement = evenementService.getEvenementById(detailAccident.getEventid());
//             evenement.setStatutEvent(DetailAccident.getStatutRom());
//             evenement.setEtatEvent(DetailAccident.getStatutRom());
            evenementService.saveEvenement(evenement);
            redirAttrs.addFlashAttribute("success", "DetailAccident modifier avec Succes.");

        }else{
            redirAttrs.addFlashAttribute("error", "Erreur modification DetailAccident.");
        }

        return "redirect:/trace/evenement/Edit/"+detailAccident.getEventid();
    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/trace/detailAccident/{op}/{id}")
    public String editDetailAccident(@PathVariable Integer id, @PathVariable String op, Model model){
        DetailAccident detailAccident = detailAccidentService.getDetailAccidentById(id);
        model.addAttribute("detailAccident",detailAccident);
//        addModelAttributes(model);
        return "trace/detailAccident"+op; //returns DetailAccidentEdit or DetailAccidentDetails
    }
// delete DetailAccident by id
    @GetMapping("/trace/detailAccident/delete/{id}")
    public String deleteDetailAccident(@PathVariable Integer id){
        detailAccidentService.deleteDetailAccident(id);
        return "redirect:/trace/detailAccidents";
    }






}
