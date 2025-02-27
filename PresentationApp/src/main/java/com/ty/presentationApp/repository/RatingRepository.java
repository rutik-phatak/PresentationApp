package com.ty.presentationApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.presentationApp.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer>{

}
