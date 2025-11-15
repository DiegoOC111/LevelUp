package com.example.LevelUp.repository;

import com.example.LevelUp.model.TipoDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDespachoRepository extends JpaRepository<TipoDespacho, Integer> {
}
