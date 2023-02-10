package com.RMP.resource_management.Service;

import java.util.List;
import com.RMP.resource_management.Model.User;
import com.RMP.resource_management.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo usrepo;
	
	
	@Override
    public List<User> getAllUsers() {
        List<User> list = (List<User>) usrepo.findAll();
        return list;
    }


	@Override
	public void saveUser(User user) {
		this.usrepo.save(user);	
	}


	@Override
	public void deleteUserById(long id) {
		this.usrepo.deleteById(id);
		
	}

	@Override
	public User updateUser(User user) {
		return usrepo.save(user); 
	}


	@Override
	public User getUserById(Long id) {
		return usrepo.getById(id);
	}


	/*@Override
	public User findByEmail(String uname) {
		// TODO Auto-generated method stub
		return usrepo.findByEmail();
	}*/
	
	@Override
	public User findByEmail(String email) {
		return usrepo.findByEmail(email);
	}
}
    
    
