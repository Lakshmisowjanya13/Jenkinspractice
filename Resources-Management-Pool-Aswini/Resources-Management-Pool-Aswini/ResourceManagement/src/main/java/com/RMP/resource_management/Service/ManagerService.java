package com.RMP.resource_management.Service;

import com.RMP.resource_management.Model.Manager;
import com.RMP.resource_management.Model.User;

import java.util.List;

public interface ManagerService {

    List<Manager> getAllManagerDetails();

    Manager getManagerDetails(Long id);

    void saveManager(Manager manager);

    void deleteManagerById(long id);

	//Manager findByName(String email);

	Manager findByName(String un);
}
