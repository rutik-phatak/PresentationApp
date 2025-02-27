package com.ty.presentationApp.entity;

import java.util.List;

import com.ty.presentationApp.enums.Role;
import com.ty.presentationApp.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name="user_info")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="user_info_seq")
	@SequenceGenerator(name="user_info_seq",allocationSize = 1)
	private Integer id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private Long phone;
	
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<Presentation> presentation;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;
	
	@Enumerated(EnumType.STRING)
	private Role role = Role.STUDENT;
	
	private Double userTotalScore = null;
}
