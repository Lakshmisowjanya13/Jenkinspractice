package com.RMP.resource_management.Service;

import java.util.List;

import com.RMP.resource_management.Model.User;


public interface UserService {
	
	List<User> getAllUsers();

    void saveUser(User user);
    
    void deleteUserById(long id);
    
    User updateUser(User user);
    
    User getUserById(Long id);

	User findByEmail(String uname);

    //User findByEmail(String email);

	
	
}
