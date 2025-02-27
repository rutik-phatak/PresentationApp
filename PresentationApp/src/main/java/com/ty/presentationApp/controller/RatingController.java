package com.ty.presentationApp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ty.presentationApp.dto.RatingRequest;
import com.ty.presentationApp.service.RatingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/rating")
public class RatingController {
	private RatingService ratingService;

	public RatingController(RatingService ratingService) {
		this.ratingService = ratingService;
	}
	
	@PostMapping("/rate/{uid}")
	public ResponseEntity<String> rate(@PathVariable int uid,@RequestParam int pid,@RequestBody RatingRequest ratingRequest) {
		String res = ratingService.rate(uid,pid,ratingRequest);
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	@PostMapping("/get/{uid}")
	public ResponseEntity<Double> postMethodName(@PathVariable int uid,@RequestParam int pid) {
		Double rating = ratingService.get(uid,pid);
		return new ResponseEntity<Double>(rating,HttpStatus.OK);
	}
	
	@PostMapping("/getAll/{uid}")
	public ResponseEntity<Double> getAll(@PathVariable int uid,@RequestParam int sid) {
		Double res = ratingService.getAll(uid,sid);
		return new ResponseEntity<Double>(res,HttpStatus.OK);
	}
	
}
