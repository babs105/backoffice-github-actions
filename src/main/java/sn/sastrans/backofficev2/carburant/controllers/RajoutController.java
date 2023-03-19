package sn.sastrans.backofficev2.carburant.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.carburant.models.Rajout;
import sn.sastrans.backofficev2.carburant.services.CuveService;
import sn.sastrans.backofficev2.carburant.services.RajoutService;
import sn.sastrans.backofficev2.carburant.services.StationService;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class RajoutController {

    @Autowired
    private RajoutService rajoutService;

    @Autowired
    private CuveService cuveService;
    @Autowired
    private StationService stationService;

    public Model addModelAttributes(Model model){

//        model.addAttribute("cuves",cuveService.getAllCuve());
        model.addAttribute("stations",stationService.getAllStation());
//        model.addAttribute("rajout", new Rajout());
        return model;
    }

    // get list Rajouts
    @GetMapping("/carburant/rajouts")
    public String getAllRajout(Model model){
        model.addAttribute("rajouts", rajoutService.getAllRajout());
//        addModelAttributes(model);
       return "carburant/rajouts";
    }

    // show form add
    @GetMapping("/carburant/rajoutAdd")
    public String showFormAddRajout(Rajout rajout, Model model){

//        model.addAttribute("rajout", rajout);
        addModelAttributes(model);
        return "carburant/rajoutAdd";
    }
    //Add Rajout
    @PostMapping("/carburant/rajouts")
    public String addRajout(@Valid Rajout rajout, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "carburant/rajoutAdd";
        }
         Rajout rajoutSaved = rajoutService.saveRajout(rajout);
         if(rajoutSaved != null){
             redirAttrs.addFlashAttribute("success", "Rajout cree avec Succes.");
         }else{
             redirAttrs.addFlashAttribute("error", "Erreur creation Rajout.");
         }
        return "redirect:/carburant/rajouts";
    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/carburant/rajout/{op}/{id}")
    public String editRajout(@PathVariable Integer id, @PathVariable String op, Model model){
        Rajout rajout = rajoutService.getRajoutById(id);
        model.addAttribute("rajout", rajout);
        addModelAttributes(model);
        return "/carburant/rajout"+op; //returns RajoutEdit or RajoutDetails
    }
// delete Rajout by id
    @GetMapping("/carburant/rajout/delete/{id}")
    public String deleteRajout(@PathVariable Integer id){
        rajoutService.deleteRajout(id);
        return "redirect:/carburant/rajouts";
    }

// for in one page
    @GetMapping("/carburant/rajout/{id}")
    @ResponseBody
    public Rajout editRajout(@PathVariable Integer id) {
        Rajout rajout = rajoutService.getRajoutById(id);
        return rajout;
    }

    //Add Rajout in one page
    @PostMapping("/carburant/inonepageRajouts")
    public String addRajoutInOnePage(@Valid Rajout rajout, BindingResult result, RedirectAttributes redirAttrs){
//        if (result.hasErrors()) {
//            return "carburant/RajoutAdd";
//        }
        Rajout rajoutSaved = rajoutService.saveRajout(rajout);
        if(rajoutSaved != null){
            redirAttrs.addFlashAttribute("success", "Rajout cree avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur creation Rajout.");
        }
        return "redirect:/carburant/rajouts";
    }
}
