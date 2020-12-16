package com.example.petclinic.pets.controller;

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
import com.example.petclinic.pets.entity.Type;
import com.example.petclinic.pets.repository.PetRepository;
import com.example.petclinic.pets.repository.TypeRepository;

@Controller
public class PetController {

	@Autowired
	PetRepository petRepo;

	@Autowired
	OwnerRepository ownerRepo;

	@Autowired
	TypeRepository typeRepo;

	
	// create/add pet
	@PostMapping("/owners/{id}/pet/addPet")
	public String createPet(@PathVariable("id") Integer id, @ModelAttribute("pet") Pet pet) {
		Pet newPet = new Pet();
		Owner owner = ownerRepo.findById(id).get();
		newPet.setName(pet.getName());
		newPet.setBirthDate(pet.getBirthDate());
		newPet.setType(pet.getType());
		newPet.setOwner(owner);

		List<Pet> pets = petRepo.findByOwner(owner); // find list of pets which belong to one owner
		pets.add(newPet);                            // adding one more pet to the list of pets of one owner

		petRepo.save(newPet);
		owner.setPets(pets);
		ownerRepo.save(owner);

		return "redirect:/owners/viewOwnerDetails/" + newPet.getOwner().getId(); // display owner details e isto so owners/viewOwnerDetails/{id} -->View owner details

	}

	
	// new pet form
	@GetMapping("/owners/{id}/pet/showNewPetForm")
	public String createPetForm(@PathVariable("id") Integer id, Model model) {

		Owner owner = ownerRepo.findById(id).get();
		Pet pet = new Pet(); // cretaing object for the form

		List<Type> listTypes = typeRepo.findAll(); // get all animal/pet types: dog, cat....
		model.addAttribute("listTypes", listTypes);

		model.addAttribute("pet", pet);
		model.addAttribute("owner", owner);

		return "pets/showNewPetForm";

	}

	
	// show form for update pet
	@GetMapping("/owners/{id}/pet/{petId}/updatePet")
	public String updatePetForm(@PathVariable("petId") Integer petId, @PathVariable("id") Integer id, Model model) {
		Owner owner = ownerRepo.findById(id).get();
		model.addAttribute("owner", owner);

		Pet pet = petRepo.findById(petId).get();
		model.addAttribute("pet", pet);

		List<Type> listTypes = typeRepo.findAll();
		model.addAttribute("listTypes", listTypes);
		return "pets/updatePetForm";

	}

	
	// Update Pet
	@PostMapping("/owners/{id}/pet/update")
	public String updatePet(@PathVariable("id") Integer ownerId, @ModelAttribute("pet") Pet pet) {
		petRepo.save(pet);
		return "redirect:/owners/viewOwnerDetails/" + pet.getOwner().getId(); // display owner details
	}

	
	// delete pet and display owner details
	@GetMapping("/owners/{id}/pet/{petId}/delete")
	public String deletePet(@PathVariable("petId") Integer petId, @PathVariable("id") Integer id) {

		petRepo.deleteById(petId);

		return "redirect:/owners/viewOwnerDetails/" + id; // e isto so owners/viewOwnerDetails/{id} -->View owner  details

	}

}
