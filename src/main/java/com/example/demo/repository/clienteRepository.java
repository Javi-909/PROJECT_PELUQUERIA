package com.example.demo.repository;

import com.example.demo.dto.ClienteDto;
import com.example.demo.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface clienteRepository extends JpaRepository<Cliente, Integer>{


}
