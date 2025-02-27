package com.ty.presentationApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.presentationApp.dto.PresentationRequest;
import com.ty.presentationApp.dto.PresentationResponse;
import com.ty.presentationApp.entity.Presentation;
import com.ty.presentationApp.service.PresentationService;
import com.ty.presentationApp.service.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/presentation")
public class PresentationController {
	private PresentationService  presentationService;

	public PresentationController(PresentationService presentationService) {
		this.presentationService = presentationService;
	}
	
	@PostMapping("/assign")
	public ResponseEntity<String> assign(@RequestParam int uid, @RequestParam int sid, @RequestBody PresentationRequest presentationRequest) {
		String res = presentationService.assign(uid,sid,presentationRequest);
		return new ResponseEntity<String>(res,HttpStatus.CREATED);
	}
	
	@PostMapping("/get")
	public ResponseEntity<PresentationResponse> get(@RequestParam int uid, @RequestParam int pid) {
		PresentationResponse presentation = presentationService.fetchById(uid,pid);
		return new ResponseEntity<PresentationResponse>(presentation,HttpStatus.OK);
	}
	
	@PostMapping("/getAll")
	public ResponseEntity<List<Presentation>> getAll(@RequestParam int uid, @RequestParam int sid) {
		List<Presentation> list = presentationService.fetchAll(uid,sid);
		return new ResponseEntity<List<Presentation>>(list,HttpStatus.OK);
	}
	
	@PutMapping("changeStatus/{uid}")
	public ResponseEntity<String> changeStatus(@PathVariable int uid,@RequestParam int pid, @RequestParam String status) {
		String res = presentationService.changeStatus(uid,pid,status);
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	@PutMapping("score/{uid}")
	public ResponseEntity<String> setScore(@PathVariable int uid,@RequestParam int pid, @RequestParam double score) {
		String res = presentationService.setScore(uid, pid,score);
		return new ResponseEntity<String>(res,HttpStatus.OK);
		
	}
	
	
}
