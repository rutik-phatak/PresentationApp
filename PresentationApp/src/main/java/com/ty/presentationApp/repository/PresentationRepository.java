package com.ty.presentationApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.presentationApp.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

}
