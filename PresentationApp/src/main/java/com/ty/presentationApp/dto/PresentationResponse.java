package com.ty.presentationApp.dto;

import com.ty.presentationApp.enums.PresentationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PresentationResponse {

	@Override
	public String toString() {
		return "PresentationResponse [pid=" + pid + ", user_id=" + user_id + ", course=" + course + ", topic=" + topic
				+ ", presentationStatus=" + presentationStatus + ", userTotalScore=" + userTotalScore + "]";
	}

	private Integer pid;
	
	private Integer user_id;
	
	private String course;
	
	private String topic;
	
	private PresentationStatus presentationStatus;
	
	private Double userTotalScore;
}
