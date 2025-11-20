package com.example.demo.repository;


import com.example.demo.entity.ServicioPelu;
import com.example.demo.repository.projection.ServicioJoinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface servicioPeluRepository extends JpaRepository<ServicioPelu, Integer>{

    @Query(value = "SELECT sp.id AS id, s.id AS servicioId, s.nombre AS nombre, s.descripcion AS descripcion, sp.precio AS precio, sp.duracion AS duracion " +
            "FROM servicio s " +
            "JOIN serviciopelu sp ON s.id = sp.servicio_id " +
            "WHERE sp.peluqueria_id = :peluqueriaId", nativeQuery = true)
    List<ServicioJoinProjection> findServiciosByPeluqueriaId(@Param("peluqueriaId") Integer peluqueriaId);


}
