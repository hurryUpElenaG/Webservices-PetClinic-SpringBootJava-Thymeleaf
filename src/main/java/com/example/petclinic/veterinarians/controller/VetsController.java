package com.example.petclinic.veterinarians.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.petclinic.veterinarians.entity.Specialty;
import com.example.petclinic.veterinarians.entity.Veterinarians;
import com.example.petclinic.veterinarians.repository.SpecialtyRepository;
import com.example.petclinic.veterinarians.repository.VetsRepository;


@Controller
public class VetsController {

	@Autowired
	VetsRepository vetsRepo;

	@Autowired
	SpecialtyRepository specialtyRepo;

	// get all veterinarians
	@GetMapping("/veterinarians")
	public String viewVeterinarians(Model model) {

		List<Veterinarians> listVeterinarians = vetsRepo.findAll(); // get list of veterinarians
		model.addAttribute("listVeterinarians", listVeterinarians);

		List<Specialty> listSpecialties = specialtyRepo.findAll(); // get list of Specialties
		model.addAttribute("listSpecialties", listSpecialties);

		return "vets/veterinarians_home";

	}

	// create veterinarian
	@PostMapping("/addVeterinarian")
	public String createVeterinarian(@ModelAttribute("veterinarian") Veterinarians veterinarian) {

		vetsRepo.save(veterinarian);

		return "vets/veterinarianDetails";
	    //return "redirect:/veterinarians";

	}

	// new veterinarian form
	@GetMapping("/showNewVeterinarianForm")
	public String showNewVeterinarianForm(Model model) {

		Veterinarians veterinarian = new Veterinarians(); // creating object for the form
		model.addAttribute("veterinarian", veterinarian);

		List<Specialty> listSpecialties = specialtyRepo.findAll(); // get list of Specialties
		model.addAttribute("listSpecialties", listSpecialties);

		return "vets/new_veterinarianForm";
	}

	// show form for update veterinarian
	@GetMapping("/showUpdateVeterinarianForm/{id}")
	public String showUpdateVeterinarianForm(@PathVariable(value = "id") Integer id, Model model) {
		
		Veterinarians veterinarian = vetsRepo.findById(id).get();
        model.addAttribute("veterinarian", veterinarian);
        
        List<Specialty> listSpecialties = specialtyRepo.findAll(); // get list of Specialties
		model.addAttribute("listSpecialties", listSpecialties);

		return "vets/updateVeterinarian";
	}
	
	// update veterinarian
	@PostMapping("/updateVeterinarian")
	public String updateVeterinarian(@ModelAttribute("veterinarian") Veterinarians veterinarian) {

		vetsRepo.save(veterinarian);

		//return "vets/veterinarianDetails";
	    return "redirect:/veterinarians";

	}
	
	
	//view details for Veterinarian
	@GetMapping("/viewVetDetails/{id}")
    public String viewVetDetails(@PathVariable(value = "id") Integer id, Model model) {
		
		Veterinarians veterinarian = vetsRepo.findById(id).get();
        model.addAttribute(veterinarian);
        
		return "vets/veterinarianDetails";
		
	}

	// delete Veterinarian
	@GetMapping("/deleteVeterinarian/{id}")
	public String deleteVeterinarian(@PathVariable("id") Integer id) {
		vetsRepo.deleteById(id);
		
		return "redirect:/veterinarians";
		
	}

}
