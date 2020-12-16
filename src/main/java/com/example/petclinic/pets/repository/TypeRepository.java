package com.example.petclinic.pets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.petclinic.pets.entity.Type;


@Repository
public interface TypeRepository  extends JpaRepository<Type, Integer> {


}