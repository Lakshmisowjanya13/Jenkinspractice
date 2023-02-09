package com.RMP.resource_management.Service;

import java.util.List;

import com.RMP.resource_management.Model.navigation;

public interface NavService {
	
	List<navigation> getAllNavs();

    void saveNav(navigation nav);
    
    navigation getNavById(Long id);

}
