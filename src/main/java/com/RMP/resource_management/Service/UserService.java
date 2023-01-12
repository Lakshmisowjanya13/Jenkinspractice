package com.RMP.resource_management.Service;

import java.util.List;

//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.RMP.resource_management.Model.Share;
import com.RMP.resource_management.Model.User;

public interface UserService {
	
	 List<User> getAllUserDetails();

	    User getUserDetails(Long id);

	    void saveUserDetails(User user);

	    void deleteUserById(long id);

	    User findById(Long id);

		User findByEmail(String uname);
		//User findUserByEmail(String email);

		User findByName(String name);

		//User findByName(String name);

		//UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException;

		//User findByEmail(String email);

		//List<User> findById(Long id);

		//void save(User user);

}
