package com.example.petclinic.owners.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.petclinic.owners.entity.Owner;
import com.example.petclinic.owners.repository.OwnerRepository;
import com.example.petclinic.pets.entity.Pet;
import com.example.petclinic.pets.entity.Visit;
import com.example.petclinic.pets.repository.PetRepository;
import com.example.petclinic.pets.repository.VisitRepository;


@Controller
public class OwnerController {
	
	@Autowired
	OwnerRepository ownerRepo;
	
	@Autowired
	PetRepository petRepo;
	
	@Autowired
	VisitRepository visitRepo;
	
	//find owner page -Home page for owner
	@GetMapping("/owners/find")
	public String findOwnersPage(Model model) {
		Owner owner = new Owner();
		model.addAttribute("owner", owner);
		return "owners/findOwners_home";
	}
	
	
	//get list of all owners
	@GetMapping("/owners/getAllOwners")
	public String getAllOwners(Model model) {
		
		List<Owner> listOwners = ownerRepo.findAll();
		model.addAttribute("listOwners", listOwners);
		
		return "owners/ownersList";
	}
	
	// create owner
	@PostMapping("/owners/addOwner")
	public String createOwner(@ModelAttribute("owner") Owner owner) {

		ownerRepo.save(owner);

		return "owners/ownerDetails";
	}

	
	// new owner form
	@GetMapping("/owners/showNewOwnerForm")
	public String showNewOwnerForm(Model model) {

		Owner owner = new Owner(); // creating object for the form
		model.addAttribute("owner", owner);

		return "owners/showNewOwnerForm";
	}
	
	
	// show form for update owner
	@GetMapping("/owners/updateOwner/{id}")
	public String showUpdateOwnerForm(@PathVariable(value = "id") Integer id, Model model) {
		
		Owner owner = ownerRepo.findById(id).get();
        model.addAttribute("owner", owner);
        
		return "owners/updateOwner";
	}
	
	
	//view details for owner- I am using it in ownersList.html for displaying owner details page
	@GetMapping("/owners/viewOwnerDetails/{id}")
    public String viewOwnerDetails(@PathVariable(value = "id") Integer id, Model model) {
		
		Owner owner = ownerRepo.findById(id).get();
		
		List<Pet> petList = petRepo.findByOwner(owner);
	
		for (Pet pet : petList) {
			
			List<Visit> visits = visitRepo.findByPetId(pet.getId());
			pet.setVisits(visits);
		}
		
        model.addAttribute(owner);
        model.addAttribute("petList", petList);
        
		return "owners/ownerDetails";
		
	}
	
	

	// delete owner
	@GetMapping("/owners/deleteOwner/{id}")
	public String deleteOwner(@PathVariable("id") Integer id) {
		ownerRepo.deleteById(id);
		
		return "redirect:/owners/getAllOwners";
		
	}

	
	//search owner by last name
	@GetMapping("/owners/searchByLastName")  
	public String searchOwnersByLastName(Model model, Owner owner) {
		
		// find owners by last name
		List<Owner> resultsOwners = ownerRepo.searchByLastName(owner.getLastName());
		
		//return all records possible in database table when we leave empty field for last name
		if (owner.getLastName().equals("")) {
			List<Owner> listOwners = ownerRepo.findAll();
			model.addAttribute("listOwners", listOwners);
			return "owners/ownersList";
		}
		
		if (resultsOwners.isEmpty()) {        //if no owners are found after typing a lastname
			// no owners found
			List<Owner> listOwners = ownerRepo.findAll();
			model.addAttribute("listOwners", listOwners);
			return "owners/findOwners_home";
			
		}
		else if (resultsOwners.size() == 1) {
			// 1 owner found
			owner = resultsOwners.iterator().next();      //iterira niz resultsOwners, no bidejki znaeme deka e samo eden rezultat mozeme da go zapiseme i kako resultsOwners.get(0).getId()
		    return "redirect:/owners/viewOwnerDetails/" + owner.getId(); //Here we use display owners info method --> /owners/viewOwnerDetails/{id}  kade {id} vo nasiot slucaj e owner.getId();
			//return "redirect:/owners/viewOwnerDetails/" + resultsOwners.get(0).getId();   // ovde e primenet metodot /owners/viewOwnerDetails/{id} kade za id imame + resultsOwners.get(0).getId()
					
		}
		else {
			// multiple owners found
			model.addAttribute("listOwners", resultsOwners);
			return "owners/ownersList";
		}
	}
	
	

}
