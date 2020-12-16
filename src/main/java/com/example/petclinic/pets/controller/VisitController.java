package com.example.petclinic.pets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.petclinic.pets.entity.Pet;
import com.example.petclinic.pets.entity.Visit;
import com.example.petclinic.pets.repository.PetRepository;
import com.example.petclinic.pets.repository.VisitRepository;

@Controller
public class VisitController {

	@Autowired
	VisitRepository visitRepo;

	@Autowired
	PetRepository petRepo;

	// new visit form
	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/showNewVisitsForm")
	public String showNewVisitsForm(@PathVariable("petId") Integer petId, Model model) {

		Visit visit = new Visit();
		Pet pet = petRepo.findById(petId).get();
		List<Visit> visits = visitRepo.findByPetId(petId);

		pet.setVisits(visits);

		model.addAttribute("pet", pet);
		model.addAttribute("visit", visit);
		return "visits/showNewVisitsForm";

	}

	// create/add visit
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/addVisit")
	public String createVisit(@PathVariable("petId") Integer petId, @ModelAttribute("visit") Visit visit) {

		Visit createVisit = new Visit();
		
		Pet pet = petRepo.findById(petId).get();
		createVisit.setVisit_date(visit.getVisit_date());
		createVisit.setDescription(visit.getDescription());
		createVisit.setPetId(petId);

		List<Visit> visits = visitRepo.findByPetId(petId);
		visits.add(createVisit);
		visitRepo.save(createVisit);
		
		pet.setVisits(visits);
		petRepo.save(pet);
		
		return "redirect:/owners/viewOwnerDetails/" + pet.getOwner().getId();    //display owner details
	}

}
