package com.senai.almoxarifado_Mister.repositories;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {
}
