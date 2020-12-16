package com.example.petclinic.owners.entity;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.petclinic.pets.entity.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "owner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String firstName;
	String lastName;
	String address;
	String city; 
	
	String phone;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id")
	private List<Pet> pets;
	
}
