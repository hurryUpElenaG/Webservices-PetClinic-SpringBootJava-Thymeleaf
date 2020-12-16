package com.example.petclinic.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petclinic.pets.entity.Pet;
import com.example.petclinic.pets.entity.Visit;


public interface VisitRepository extends JpaRepository<Visit, Integer> {
	
	//List<Visit> findByPetId(Pet pet);
	
	List<Visit> findByPetId(Integer petId);

}
