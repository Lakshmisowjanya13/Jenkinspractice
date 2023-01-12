package com.RMP.resource_management.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.RMP.resource_management.Model.Employee;
//import com.RMP.resource_management.Model.Share;
import com.RMP.resource_management.Model.User;
//import com.RMP.resource_management.Repository.ShareRepository;
import com.RMP.resource_management.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

@Autowired
private UserRepository ur;

@Override
public List<User> getAllUserDetails() {
    return ur.findAll();
}

@Override
public User findById(Long id) {
	return ur.findById(id).orElse(null);
}

@Override
public User findByEmail(String email) {
	return ur.findByEmail(email);
}

/*@Override
public User findByEmail(String  email) {
	return (ur.findByEmail(email)).orElse(null);
}*/

@Override
public User getUserDetails(Long id) {
	return (User) ur.findById(id).orElse(null);
}

@Override
public void saveUserDetails(User user) {
    ur.save(user);

}

@Override
public void deleteUserById(long id) {
    this.ur.deleteById(id);
}

@Override
public User findByName(String name) {
		return ur.findByName(name);
}


/*@Override
public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

    User user = ur.findByEmail(usernameOrEmail);
    if(user != null){
        return new org.springframework.security.core.userdetails.User(user.getEmail()
                , user.getPassword(), false, false, false, false, null);
                
    }else {
        throw new UsernameNotFoundException("Invalid email or password");
    }
}*/

}