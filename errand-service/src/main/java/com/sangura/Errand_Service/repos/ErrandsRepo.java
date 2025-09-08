package com.sangura.Errand_Service.repos;

import org.springframework.data.repository.ListCrudRepository;

import com.sangura.Errand_Service.entities.Errand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ErrandsRepo extends JpaRepository<Errand, Long> {

}
