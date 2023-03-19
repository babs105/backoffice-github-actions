package sn.sastrans.backofficev2.carburant.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.carburant.models.Station;
import sn.sastrans.backofficev2.carburant.services.StationService;
import sn.sastrans.backofficev2.parameters.services.LocationService;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class StationController {

    @Autowired
    private StationService stationService;
    @Autowired
    private LocationService locationService;

    public Model addModelAttributes(Model model){
        model.addAttribute("stations", stationService.getAllStation());
        model.addAttribute("locations", locationService.getAllLocation());
        model.addAttribute("station", new Station());
        return model;
    }

    // get list stations
    @GetMapping("/carburant/stations")
    public String getAllstation(Model model){
        addModelAttributes(model);
//       return "/carburant/stations";
       return "carburant/stationsInonepage";

    }
    // show form add
//    @GetMapping("/carburant/stationAdd")
//    public String showFormAddstation(Model model){
//
////        model.addAttribute("station", station);
//        addModelAttributes(model);
//        return "/carburant/stationAdd";
//    }
//    //Add station
//    @PostMapping("/carburant/stations")
//    public String addstation(@Valid Station station, BindingResult result, RedirectAttributes redirAttrs){
//        if (result.hasErrors()) {
//            return "carburant/stationAdd";
//        }
//         Station stationSaved = stationService.saveStation(station);
//         if(stationSaved != null){
//             redirAttrs.addFlashAttribute("success", "station cree avec Succes.");
//         }else{
//             redirAttrs.addFlashAttribute("error", "Erreur creation station.");
//         }
//        return "redirect:/carburant/stations";
//    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/carburant/station/{op}/{id}")
    public String editstation(@PathVariable Integer id, @PathVariable String op, Model model){
        Station station = stationService.getStationById(id);
        model.addAttribute("station", station);
        addModelAttributes(model);
        return "carburant/station"+op; //returns stationEdit or stationDetails
    }
// delete station by id
    @GetMapping("/carburant/station/delete/{id}")
    public String deletestation(@PathVariable Integer id){
        stationService.deleteStation(id);
        return "redirect:/carburant/stations";
    }

// for in one page
    @GetMapping("/carburant/station/{id}")
    @ResponseBody
    public Station editstation(@PathVariable Integer id) {
        Station station = stationService.getStationById(id);
        return station;
    }

    //Add station in one page
    @PostMapping("/carburant/stationsInonepage")
    public String addstationInOnePage(@Valid Station station, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "carburant/stationsInonepage";
        }
        Station stationSaved = stationService.saveStation(station);
        if(stationSaved != null){
            redirAttrs.addFlashAttribute("success", "station cree avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur creation station.");
        }
        return "redirect:/carburant/stations";
    }
}
