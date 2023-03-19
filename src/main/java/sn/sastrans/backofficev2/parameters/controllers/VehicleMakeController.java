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
import sn.sastrans.backofficev2.parameters.models.VehicleMake;
import sn.sastrans.backofficev2.parameters.services.VehicleMakeService;

import javax.validation.Valid;

@Controller
public class VehicleMakeController {

    @Autowired
    private VehicleMakeService vehicleMakeService;

    public Model addModelAttributes(Model model){
        model.addAttribute("vehicleMakes", vehicleMakeService.getAllVehicleMake());
        model.addAttribute("vehicleMake", new VehicleMake());
        return model;
    }

    // get list VehicleMakes
    @GetMapping("/parameters/vehicleMakes")
    public String getAllVehicleMake(Model model){
        addModelAttributes(model);
//        return "/parameters/vehicleMakes";
        return "parameters/vehicleMakesInonepage";

    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/parameters/vehicleMake/{op}/{id}")
    public String editVehicleMake(@PathVariable Integer id, @PathVariable String op, Model model){
        VehicleMake vehicleMake = vehicleMakeService.getVehicleMakeById(id);
        model.addAttribute("vehicleMake", vehicleMake);
//        addModelAttributes(model);
        return "parameters/vehicleMake"+op; //returns VehicleMakeEdit or VehicleMakeDetails
    }
// delete VehicleMake by id
    @GetMapping("/parameters/vehicleMake/delete/{id}")
    public String deleteVehicleMake(@PathVariable Integer id){
        vehicleMakeService.deleteVehicleMake(id);
        return "redirect:/parameters/vehicleMakes";
    }

    // for in one page
    @GetMapping("/parameters/vehicleMake/{id}")
    @ResponseBody
    public VehicleMake getVehicleMakeById(@PathVariable Integer id) {
        VehicleMake vehicleMake = vehicleMakeService.getVehicleMakeById(id);
        return vehicleMake;
    }

    //Add VehicleMake in one page
    @PostMapping("/parameters/vehicleMakesInonepage")
    public String addVehicleMakeInOnePage(@Valid VehicleMake vehicleMake, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "parameters/vehicleMakesInonepage";
        }
        VehicleMake vehicleMakeSaved = vehicleMakeService.saveVehicleMake(vehicleMake);
        if(vehicleMakeSaved != null){
            redirAttrs.addFlashAttribute("success", "VehicleMake cree avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur creation VehicleMake.");
        }
        return "redirect:/parameters/vehicleMakes";
    }
}
