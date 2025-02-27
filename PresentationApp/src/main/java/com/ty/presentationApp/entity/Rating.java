package com.ty.presentationApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="rating_seq")
	@SequenceGenerator(name="rating_seq",allocationSize = 1)
	private Integer rid;
	
	private Integer communication;
	
	private Integer confidence;
	
	private Integer content;
	
	private Integer interaction;
	
	private Integer liveliness;
	
	private Integer usageProps;
	
	private Double totalScore;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Presentation presentation;
}
