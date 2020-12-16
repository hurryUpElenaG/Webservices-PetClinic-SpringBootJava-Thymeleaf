package com.example.petclinic.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.petclinic.owners.entity.Owner;
import com.example.petclinic.pets.entity.Pet;



@Repository
public interface PetRepository  extends JpaRepository<Pet, Integer> {

	List<Pet> findByOwner(Owner owner);

}
