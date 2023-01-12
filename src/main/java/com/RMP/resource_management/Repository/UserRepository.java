package com.RMP.resource_management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.RMP.resource_management.Model.User;


public interface UserRepository extends JpaRepository<User,Long> {
	
	 //@Query("SELECT u FROM user u WHERE u.email = ?1")
	   //public User findByEmail(String email);

	//Optional<User> findByEmail(String email);

	//Optional<User> findByEmail(String email);
	User findByEmail(String email);
    User findByName(String username);
}

