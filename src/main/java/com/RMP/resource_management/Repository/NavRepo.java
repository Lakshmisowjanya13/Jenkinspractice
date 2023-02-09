package com.RMP.resource_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RMP.resource_management.Model.navigation;

public interface NavRepo extends JpaRepository<navigation,Long> {

}
