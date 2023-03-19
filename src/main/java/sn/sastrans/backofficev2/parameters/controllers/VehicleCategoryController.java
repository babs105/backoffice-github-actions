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
import sn.sastrans.backofficev2.parameters.models.VehicleCategory;
import sn.sastrans.backofficev2.parameters.services.VehicleCategoryService;

import javax.validation.Valid;

@Controller
public class VehicleCategoryController {

    @Autowired
    private VehicleCategoryService vehicleCategoryService;

    public Model addModelAttributes(Model model){
        model.addAttribute("vehicleCategories", vehicleCategoryService.getAllVehicleCategory());
        model.addAttribute("vehicleCategory", new VehicleCategory());
        return model;
    }

    // get list VehicleCategorys
    @GetMapping("/parameters/vehicleCategories")
    public String getAllVehicleCategory(Model model){
        addModelAttributes(model);
//        return "/parameters/vehicleCategories";
        return "parameters/vehicleCategoriesInonepage";

    }
    // show form add
//    @GetMapping("/parameters/vehicleCategoryAdd")
//    public String showFormAddVehicleCategory(VehicleCategory vehicleCategory, Model model){
//
//        model.addAttribute("vehicleCategory", vehicleCategory);
//        return "parameters/vehicleCategoryAdd";
//    }
    //Add VehicleCategory
//    @PostMapping("/parameters/vehicleCategories")
//    public String addVehicleCategory(@Valid VehicleCategory vehicleCategory, BindingResult result, RedirectAttributes redirAttrs){
//        if (result.hasErrors()) {
//            return "parameters/vehicleCategoryAdd";
//        }
//         VehicleCategory vehicleCategorysaved = vehicleCategoryService.saveVehicleCategory(vehicleCategory);
//         if(vehicleCategorysaved != null){
//             redirAttrs.addFlashAttribute("success", "VehicleCategory cree avec Succes.");
//         }else{
//             redirAttrs.addFlashAttribute("error", "Erreur creation VehicleCategory.");
//         }
//        return "redirect:/parameters/vehicleCategorys";
//    }

    //The op parameter is either Edit or Details show form
    @GetMapping("/parameters/vehicleCategory/{op}/{id}")
    public String editVehicleCategory(@PathVariable Integer id, @PathVariable String op, Model model){
        VehicleCategory vehicleCategory = vehicleCategoryService.getVehicleCategoryById(id);
        model.addAttribute("vehicleCategory", vehicleCategory);
//        addModelAttributes(model);
        return "parameters/vehicleCategory"+op; //returns VehicleCategoryEdit or VehicleCategoryDetails
    }
// delete VehicleCategory by id
    @GetMapping("/parameters/vehicleCategory/delete/{id}")
    public String deleteVehicleCategory(@PathVariable Integer id){
        vehicleCategoryService.deleteVehicleCategory(id);
        return "redirect:/parameters/vehicleCategories";
    }

// for in one page
    @GetMapping("/parameters/vehicleCategory/{id}")
    @ResponseBody
    public VehicleCategory editVehicleCategory(@PathVariable Integer id) {
        VehicleCategory vehicleCategory = vehicleCategoryService.getVehicleCategoryById(id);
        return vehicleCategory;
    }

    //Add VehicleCategory in one page
    @PostMapping("/parameters/vehicleCategoriesInonepage")
    public String addVehicleCategoryInOnePage(@Valid VehicleCategory vehicleCategory, BindingResult result, RedirectAttributes redirAttrs){
        if (result.hasErrors()) {
            return "parameters/vehicleCategoriesInonepage";
        }
        VehicleCategory vehicleCategorysaved = vehicleCategoryService.saveVehicleCategory(vehicleCategory);
        if(vehicleCategorysaved != null){
            redirAttrs.addFlashAttribute("success", "VehicleCategory cree avec Succes.");
        }else{
            redirAttrs.addFlashAttribute("error", "Erreur creation VehicleCategory.");
        }
        return "redirect:/parameters/vehicleCategories";
    }
}
