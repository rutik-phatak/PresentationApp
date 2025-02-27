package com.ty.presentationApp.controller; 
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.presentationApp.dto.UserRequest;
import com.ty.presentationApp.dto.UserResponse;
import com.ty.presentationApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
		boolean registered =  userService.saveUser(userRequest);
		if(registered) {
			return new ResponseEntity<String>("registered",HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("email already registered",HttpStatus.BAD_REQUEST);	
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email,@RequestParam String password) {
		boolean valid =  userService.login(email,password);
		if(valid) {
			return new ResponseEntity<String>("login successful",HttpStatus.OK);
		}
		return new ResponseEntity<String>("login failed",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/get")
	public ResponseEntity<?> get(@RequestParam int id) {
		UserResponse userResponse = userService.get(id);
		if(userResponse != null) {
			return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("User is not active",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/getAll")
	public ResponseEntity<List<UserResponse>> getAll(@RequestParam int id) {
		List<UserResponse> list = userService.getAll(id);
		return new ResponseEntity<List<UserResponse>>(list,HttpStatus.OK);
	}
	
	@PostMapping("/updateStatus/{uid}")
	public ResponseEntity<String> updateStatus(@PathVariable int uid, @RequestParam int sid, @RequestParam String status) {
		String res = userService.updateStatus(uid,sid,status);
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	@PostMapping("/sendMail/{uid}")
	public ResponseEntity<String> sendMail(@PathVariable int uid,@RequestParam int sid) {
		String res = userService.sendMail(uid,sid);
		return new ResponseEntity<String>(res,HttpStatus.OK); 
	}
	
	
}
