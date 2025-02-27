package com.ty.presentationApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ty.presentationApp.dto.UserRequest;
import com.ty.presentationApp.dto.UserResponse;
import com.ty.presentationApp.entity.User;
import com.ty.presentationApp.enums.Role;
import com.ty.presentationApp.enums.Status;
import com.ty.presentationApp.exception.UserNotAnAdminException;
import com.ty.presentationApp.exception.UserNotFoundException;
import com.ty.presentationApp.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private PresentationService presentationService;
	private JavaMailSender javaMailSender;

	public UserService(UserRepository userRepository, PresentationService presentationService,
			JavaMailSender javaMailSender) {
		this.userRepository = userRepository;
		this.presentationService = presentationService;
		this.javaMailSender = javaMailSender;
	}

	public boolean saveUser(UserRequest userRequest) {
		Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
		if(user.isPresent()) {
			return false;
		}else {
			User u = new User();
			BeanUtils.copyProperties(userRequest, u);
			userRepository.save(u);
			return true;
		}
	}

	public boolean login(String email, String password) {
		User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
		if(user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public UserResponse get(int id) {
		User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("user not found"));
		if(user.getStatus().equals(Status.ACTIVE)) {
			UserResponse ur = new UserResponse();
			BeanUtils.copyProperties(user, ur);
			return ur;
		}
		return null;
	}

	public List<UserResponse> getAll(int id) {
		User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("user not found"));
		if(user.getStatus().equals(Status.ACTIVE) && user.getRole().equals(Role.ADMIN)) {
			List<User> list = userRepository.findAll();
			List<UserResponse> responseList = new ArrayList<>();
			for (User u : list) {
				UserResponse ur = new UserResponse();
				BeanUtils.copyProperties(u, ur);
				responseList.add(ur);
			}
			return responseList;
		}else {
			throw new UserNotAnAdminException("User is not an admin or else inactive admin");
		}
	}

	public String updateStatus(int uid, int sid, String status) {
		if(presentationService.isAdmin(uid)) {
			User student = userRepository.findById(sid).orElseThrow(()->new UserNotFoundException("Student with id "+sid+" not found"));
			student.setStatus(Status.valueOf(status.toUpperCase()));
			return "status changed to "+status;
		}else {
			throw new UserNotAnAdminException("User not an admin");
		}
	}

	public String sendMail(int uid,int sid) {
		if(presentationService.isAdmin(uid)) {
			User student = userRepository.findById(sid).orElseThrow(()->new UserNotFoundException("Student not found"));
			SimpleMailMessage message=new SimpleMailMessage();
			message.setTo(student.getEmail());
			message.setSubject("Presentation Score");
			message.setText("Your overall score is "+student.getUserTotalScore());
			
			javaMailSender.send(message);
			return "Mail send successfully";
		}else {
			throw new UserNotAnAdminException("User not an admin");
		}
	}
	
	
	
	
}
