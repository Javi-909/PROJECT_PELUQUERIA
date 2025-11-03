package com.example.demo.repository;


import com.example.demo.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface horarioRepository extends JpaRepository<Horario, Integer> {




}
