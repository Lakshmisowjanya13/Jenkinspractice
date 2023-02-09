package com.RMP.resource_management.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RMP.resource_management.Model.navigation;
import com.RMP.resource_management.Repository.NavRepo;

@Service
public class NavServiceImpl implements NavService  {

	@Autowired
	NavRepo navrepo;
	
	@Override
	public List<navigation> getAllNavs() {
		List<navigation> list=(List<navigation>) navrepo.findAll();
		return list;
	}

	@Override
	public void saveNav(navigation nav) {
		// TODO Auto-generated method stub
		this.navrepo.save(nav);	
	}

	@Override
	public navigation getNavById(Long id) {
		return navrepo.getById(id);
	}

}
