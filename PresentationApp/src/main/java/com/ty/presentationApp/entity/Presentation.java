package com.ty.presentationApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ty.presentationApp.enums.PresentationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Presentation {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="presentation_seq")
	@SequenceGenerator(name="presentation_seq",allocationSize = 1)
	private Integer pid;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
	
	private String course;
	
	private String topic;
	
	@Enumerated(EnumType.STRING)
	private PresentationStatus presentationStatus = PresentationStatus.ASSIGNED;
	
	private Double userTotalScore = null;
}
