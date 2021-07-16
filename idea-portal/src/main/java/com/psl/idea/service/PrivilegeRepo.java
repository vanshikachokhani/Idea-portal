package com.psl.idea.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.idea.models.Privilege;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {

}
