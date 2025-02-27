package com.ty.presentationApp.dto;

import java.util.List;

import com.ty.presentationApp.entity.Presentation;
import com.ty.presentationApp.enums.Role;
import com.ty.presentationApp.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	private Integer id;
	
	private String name;
	
	private String email;
	
	private Long phone;
	
	private Status status;
	
	private Role role;
	
	private Double userTotalScore;
}
