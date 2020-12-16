package com.example.petclinic.veterinarians.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.petclinic.veterinarians.entity.Specialty;


@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

}