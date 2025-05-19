package com.tattou.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tattou.model.Tatuaje;

@Repository
public interface TatuajeRepository extends JpaRepository<Tatuaje, Long> {

    List<Tatuaje> findByAutorId(Long autorId);

}
