package com.RMP.resource_management.Repository;

import com.RMP.resource_management.Model.Manager;
import com.RMP.resource_management.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
    //Manager getByName(String un);

	Manager findByName(String email);

	//Manager findByName(String un);

}
