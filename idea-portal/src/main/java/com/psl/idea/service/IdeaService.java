package com.psl.idea.service;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

public class IdeaService extends JpaRepositoryFactory{

	public IdeaService(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

}
