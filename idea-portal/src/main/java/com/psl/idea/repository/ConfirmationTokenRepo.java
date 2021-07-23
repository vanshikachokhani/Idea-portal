package com.psl.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Long>{
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
