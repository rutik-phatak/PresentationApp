package com.ty.presentationApp.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ty.presentationApp.dto.PresentationRequest;
import com.ty.presentationApp.dto.PresentationResponse;
import com.ty.presentationApp.entity.Presentation;
import com.ty.presentationApp.entity.User;
import com.ty.presentationApp.enums.PresentationStatus;
import com.ty.presentationApp.enums.Role;
import com.ty.presentationApp.exception.PresentationNotFoundException;
import com.ty.presentationApp.exception.UserNotAnAdminException;
import com.ty.presentationApp.exception.UserNotFoundException;
import com.ty.presentationApp.repository.PresentationRepository;
import com.ty.presentationApp.repository.UserRepository;

@Service
public class PresentationService {
	private PresentationRepository presentationRepository;
	private UserRepository userRepository;

	public PresentationService(PresentationRepository presentationRepository, UserRepository userRepository) {
		this.presentationRepository = presentationRepository;
		this.userRepository = userRepository;
	}

	public String assign(int uid, int sid, PresentationRequest presentationRequest) {
		if (isAdmin(uid)) {
			User student = userRepository.findById(sid)
					.orElseThrow(() -> new UserNotFoundException("Student not found"));
			Presentation p = new Presentation();
			BeanUtils.copyProperties(presentationRequest, p);
			p.setUser(student);
			presentationRepository.save(p);
			return "presentation with id " + p.getPid() + " assigned to student id " + sid;

		} else {
			throw new UserNotAnAdminException("user not an admin");
		}
	}

	public PresentationResponse fetchById(int uid, int pid) {
		if (isAdmin(uid)) {
			Presentation presentation = presentationRepository.findById(pid)
					.orElseThrow(() -> new PresentationNotFoundException("Presentation not found"));
			PresentationResponse pr = new PresentationResponse();
			System.out.println(presentation);
			BeanUtils.copyProperties(presentation, pr);
			pr.setUser_id(presentation.getUser().getId());
			System.out.println(pr);
			return pr;
		} else {
			throw new UserNotAnAdminException("user not an admin");
		}

	}

	public List<Presentation> fetchAll(int uid, int sid) {
		if (isAdmin(uid)) {
			User student = userRepository.findById(sid)
					.orElseThrow(() -> new UserNotFoundException("Student not found"));
			return student.getPresentation();
		} else {
			throw new UserNotAnAdminException("user not an admin");
		}

	}

	public String changeStatus(int uid,int pid, String status) {
		User user = userRepository.findById(uid).orElseThrow(() -> new UserNotFoundException("user not found"));
		Presentation presentation = presentationRepository.findById(pid).orElseThrow(()->new PresentationNotFoundException("presentation not found"));
		if(presentation.getUser().equals(user)) {
			presentation.setPresentationStatus(PresentationStatus.valueOf(status.toUpperCase()));
			presentationRepository.save(presentation);
			return "status changed to " + presentation.getPresentationStatus();
		}else {
			throw new PresentationNotFoundException("presentation "+presentation.getPid()+" not found for student "+user.getId());
		}
		
	}

	public String setScore(int uid, int pid, double score) {
		if(isAdmin(uid)) {
			Presentation presentation = presentationRepository.findById(pid).orElseThrow(()->new PresentationNotFoundException("Presentation not found"));
			presentation.setUserTotalScore(score);
			presentationRepository.save(presentation);
			return "Score updated";
			
		}else {
			throw new UserNotAnAdminException("user not an admin");
		}
	}

	public boolean isAdmin(int id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
		return user.getRole() == Role.ADMIN;
	}

}
