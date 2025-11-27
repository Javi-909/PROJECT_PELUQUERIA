package com.example.demo.repository;

import com.example.demo.entity.Peluqueria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface peluqueriaRepository extends JpaRepository<Peluqueria, Integer>{

    Peluqueria findByEmail(String email);

}
