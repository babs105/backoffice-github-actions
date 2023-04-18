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
import sn.sastrans.backofficev2.parameters.models.VehicleModel;
import sn.sastrans.backofficev2.parameters.services.VehicleMakeService;
import sn.sastrans.backofficev2.parameters.services.VehicleModelService;

import javax.validation.Valid;

@Controller

public class VehicleModelController {

    @Autowired
    private VehicleModelService vehicleModelService;

    @Autowired
    private VehicleMakeService vehicleMakeService;

    public Model addModelAttributes(Model model){
        model.addAttribute("vehicleModels", vehicleModelService.getAllVehicleModel());
        model.addAttribute("vehicleModel", new VehicleModel());
        model.addAttribute("vehicleMakes",vehicleMakeService.getAllVehicleMake());
        return model;
    }

    // get list vehicleModels
    @GetMapping("/parameters/vehicleModels")
    public String getAllvehicleModel(Model model){
        addModelAttributes(model);
//        return "/parameters/vehicleModels";
        return "parameters/vehicleModelsInonepage";

    }
    // show form add
//    @GetMapping("/parameters/vehicleModelAdd")
//    public String showFormAddvehicleModel(VehicleModel vehicleModel, Model model){
//
//        model.addAttribute("vehicleModel", vehicleModel);
//        return "parameters/vehicleModelAdd";
//    }
    //Add vehicleModel
//    @PostMapping("/parameters/vehicleModels")
//    public String addvehicleModel(@Valid VehicleModel vehicleModel, BindingResult result, RedirectAttributes redirAttrs){
//        if (result.hasErrors()) {
//            return "parameters/vehicleModelAdd";
//        }
//         VehicleModel vehicleModelSaved = vehicleModelService.saveVehicleModel(vehicleModel);
//         if(vehicleModelSaved != null){
//             redirAttrs.addFlashAttribute("success", "vehicleModel cree avec Succes.");
//         }else{
//             redirAttrs.addFlashAttribute("error", "Erreur creation vehicleModel.");
//         }
//        return "redirect:/parameters/vehicleModels";
//    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/parameters/vehicleModel/{op}/{id}")
    public String editvehicleModel(@PathVariable Integer id, @PathVariable String op, Model model){
        VehicleModel vehicleModel = vehicleModelService.getVehicleModelById(id);
        model.addAttribute("vehicleModel", vehicleModel);
//        addModelAttributes(model);
        return "parameters/vehicleModel"+op; //returns vehicleModelEdit or vehicleModelDetails
    }
// delete vehicleModel by id
    @GetMapping("/parameters/vehicleModel/delete/{id}")
    public String deletevehicleModel(@PathVariable Integer id){
        vehicleModelService.deleteVehicleModel(id);
        return "redirect:/parameters/vehicleModels";
    }

// for in one page
    @GetMapping("/parameters/vehicleModel/{id}")
    @ResponseBody
    public VehicleModel editvehicleModel(@PathVariable Integer id) {
        VehicleModel vehicleModel = vehicleModelService.getVehicleModelById(id);
        return vehicleModel;
    }

    //Add vehicleModel in one page
    @PostMapping("/parameters/vehicleModelsInonepage")
    public String addvehicleModelInOnePage(@Valid VehicleModel vehicleModel, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "parameters/vehicleModelsInonepage";
        }
        VehicleModel vehicleModelSaved = vehicleModelService.saveVehicleModel(vehicleModel);
        if(vehicleModelSaved != null){
            redirAttrs.addFlashAttribute("success", "vehicleModel cree avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur creation vehicleModel.");
        }
        return "redirect:/parameters/vehicleModels";
    }
}
