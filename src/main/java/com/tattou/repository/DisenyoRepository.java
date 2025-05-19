package com.tattou.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tattou.model.Disenyo;

@Repository
public interface DisenyoRepository extends JpaRepository<Disenyo, Long> {

    List<Disenyo> findByAutorId(Long autorId);

}
