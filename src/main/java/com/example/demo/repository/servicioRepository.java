package com.example.demo.repository;

import com.example.demo.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface servicioRepository extends JpaRepository<Servicio, Integer>{

}
