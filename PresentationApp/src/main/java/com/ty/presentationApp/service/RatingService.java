package com.ty.presentationApp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ty.presentationApp.dto.RatingRequest;
import com.ty.presentationApp.entity.Presentation;
import com.ty.presentationApp.entity.Rating;
import com.ty.presentationApp.entity.User;
import com.ty.presentationApp.exception.PresentationNotFoundException;
import com.ty.presentationApp.exception.UserNotAnAdminException;
import com.ty.presentationApp.exception.UserNotFoundException;
import com.ty.presentationApp.repository.PresentationRepository;
import com.ty.presentationApp.repository.RatingRepository;
import com.ty.presentationApp.repository.UserRepository;

@Service
public class RatingService {
	private RatingRepository ratingRepo;
	private UserRepository userRepo;
	private PresentationRepository presentationRepo;
	private PresentationService presentationService;

	public RatingService(RatingRepository ratingRepo, UserRepository userRepo, PresentationRepository presentationRepo, PresentationService presentationService) {
		this.ratingRepo = ratingRepo;
		this.userRepo = userRepo;
		this.presentationRepo = presentationRepo;
		this.presentationService = presentationService;
	}

//	public String rate(int uid, int sid, int pid, RatingRequest ratingRequest) {
//		if(presentationService.isAdmin(uid)) {
//			User student = userRepo.findById(sid).orElseThrow(()->new UserNotFoundException("student not found"));
//			Presentation presentation = presentationRepo.findById(pid).orElseThrow(()->new PresentationNotFoundException("Presentation not found"));
//			Rating rating = new Rating();
//			BeanUtils.copyProperties(ratingRequest, rating);
//			rating.setUser(student);
//			rating.setPresentation(presentation);
//			rating.setTotalScore((double) ((rating.getCommunication()+rating.getConfidence()+rating.getContent()+rating.getInteraction()+rating.getLiveliness()+rating.getUsageProps())/6));
//			ratingRepo.save(rating);
//			return "rating saved with id "+rating.getRid();
//		}else {
//			throw new UserNotAnAdminException("User is not an admin");
//		}
//	}

	public String rate(int uid, int pid, RatingRequest ratingRequest) {
		if(presentationService.isAdmin(uid)) {
			Presentation presentation = presentationRepo.findById(pid).orElseThrow(()->new PresentationNotFoundException("Presentation not found"));
			User student = presentation.getUser();
			Rating rating = new Rating();
			BeanUtils.copyProperties(ratingRequest, rating);
			rating.setUser(student);
			rating.setPresentation(presentation);
			rating.setTotalScore((double) ((rating.getCommunication()+rating.getConfidence()+rating.getContent()+rating.getInteraction()+rating.getLiveliness()+rating.getUsageProps())/6));
			ratingRepo.save(rating);
			
			//update score in user record
			if(student.getUserTotalScore()!=null) {
				student.setUserTotalScore((student.getUserTotalScore()+rating.getTotalScore())/2);
				userRepo.save(student);
			}else {
				student.setUserTotalScore(rating.getTotalScore());
				userRepo.save(student);
			}
			//update score in presentation record
			if(presentation.getUserTotalScore()!=null) {
				presentation.setUserTotalScore((presentation.getUserTotalScore()+rating.getTotalScore())/2);
				presentationRepo.save(presentation);
			}else {
				presentation.setUserTotalScore(rating.getTotalScore());
				presentationRepo.save(presentation);
			}
			
			return "rating saved with id "+rating.getRid();
		}else {
			throw new UserNotAnAdminException("User is not an admin");
		}
	}

	public Double get(int uid, int pid) {
		if(presentationService.isAdmin(uid)) {
			Rating rating = ratingRepo.findById(pid).orElseThrow(()-> new PresentationNotFoundException("Presentation not found"));
			return rating.getTotalScore();
		}else {
			throw new UserNotAnAdminException("User is not an admin");
		}
		
	}

	public Double getAll(int uid, int sid) {
		if(presentationService.isAdmin(uid)) {
			User student = userRepo.findById(sid).orElseThrow(()->new UserNotFoundException("student not found"));
			Double totalScore = student.getUserTotalScore();
			return totalScore;
		}else {
			throw new UserNotAnAdminException("User is not an admin");
		}
	}
	
	
}
