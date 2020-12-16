package com.example.petclinic.owners.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.petclinic.owners.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer>{
	
	//get all owners by last name
	@Query("SELECT o FROM Owner o WHERE o.lastName LIKE %:lastName%")
	@Transactional(readOnly = true)
	List<Owner> searchByLastName (@Param("lastName") String lastName);
	
	//get all owners by last name
	@Query("SELECT o FROM Owner o WHERE o.lastName LIKE  %?1%")
    List<Owner> searchFindByLastName (@Param("lastName") String lastName);

}
