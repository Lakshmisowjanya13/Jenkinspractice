package com.RMP.resource_management.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.RMP.resource_management.Model.User;


public interface UserRepo extends JpaRepository<User,Long> {

	//User findByEmail();

	User findByEmail(String email);
	
	//User findByEmail(String email);
  
}

