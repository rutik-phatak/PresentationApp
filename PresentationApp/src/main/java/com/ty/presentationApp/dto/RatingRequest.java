package com.ty.presentationApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
	private Integer communication=0;
	
	private Integer confidence=0;
	
	private Integer content=0;
	
	private Integer interaction=0;
	
	private Integer liveliness=0;
	
	private Integer usageProps=0;
}
