package sn.sastrans.backofficev2.parameters.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.sastrans.backofficev2.parameters.models.VehicleStatus;
import sn.sastrans.backofficev2.parameters.services.VehicleStatusService;

import javax.validation.Valid;

@Controller

public class VehicleStatusController {

    @Autowired
    private VehicleStatusService vehicleStatusService;

    public Model addModelAttributes(Model model){
        model.addAttribute("vehiclesStatus", vehicleStatusService.getAllVehicleStatus());
        model.addAttribute("vehicleStatus", new VehicleStatus());
        return model;
    }

    // get list vehicleStatuss
    @GetMapping("/parameters/vehicleStatus")
    public String getAllvehicleStatus(Model model){
        addModelAttributes(model);
//        return "/parameters/vehicleStatus";
        return "parameters/vehicleStatusInonepage";

    }
    // show form add
//    @GetMapping("/parameters/vehicleStatusAdd")
//    public String showFormAddvehicleStatus(VehicleStatus vehicleStatus, Model model){
//
//        model.addAttribute("vehicleStatus", vehicleStatus);
//        return "parameters/vehicleStatusAdd";
//    }
    //Add vehicleStatus
//    @PostMapping("/parameters/vehicleStatuss")
//    public String addvehicleStatus(@Valid VehicleStatus vehicleStatus, BindingResult result, RedirectAttributes redirAttrs){
//        if (result.hasErrors()) {
//            return "parameters/vehicleStatusAdd";
//        }
//        VehicleStatus vehicleStatusSaved = vehicleStatusService.saveVehicleStatus(vehicleStatus);
//         if(vehicleStatusSaved != null){
//             redirAttrs.addFlashAttribute("success", "vehicleStatus cree avec Succes.");
//         }else{
//             redirAttrs.addFlashAttribute("error", "Erreur creation vehicleStatus.");
//         }
//        return "redirect:/parameters/vehicleStatuss";
//    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/parameters/vehicleStatus/{op}/{id}")
    public String editvehicleStatus(@PathVariable Integer id, @PathVariable String op, Model model){
        VehicleStatus vehicleStatus = vehicleStatusService.getVehicleStatusById(id);
        model.addAttribute("vehicleStatus", vehicleStatus);
//        addModelAttributes(model);
        return "parameters/vehicleStatus"+op; //returns vehicleStatusEdit or vehicleStatusDetails
    }
// delete vehicleStatus by id
    @GetMapping("/parameters/vehicleStatus/delete/{id}")
    public String deletevehicleStatus(@PathVariable Integer id){
        vehicleStatusService.deleteVehicleStatus(id);
        return "redirect:/parameters/vehicleStatus";
    }

// for in one page
    @GetMapping("/parameters/vehicleStatus/{id}")
    @ResponseBody
    public VehicleStatus editvehicleStatus(@PathVariable Integer id) {
        VehicleStatus vehicleStatus = vehicleStatusService.getVehicleStatusById(id);
        return vehicleStatus;
    }

    //Add vehicleStatus in one page
    @PostMapping("/parameters/vehicleStatusInonepage")
    public String addvehicleStatusInOnePage(@Valid VehicleStatus vehicleStatus, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "parameters/vehicleStatusInonepage";
        }
        VehicleStatus vehicleStatusSaved = vehicleStatusService.saveVehicleStatus(vehicleStatus);
        if(vehicleStatusSaved != null){
            redirAttrs.addFlashAttribute("success", "vehicleStatus cree avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur creation vehicleStatus.");
        }
        return "redirect:/parameters/vehicleStatus";
    }
}
