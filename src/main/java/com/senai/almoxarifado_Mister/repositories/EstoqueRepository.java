package com.senai.almoxarifado_Mister.repositories;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {
    Optional<EstoqueEntity> findByProdutoId(Long produtoId);
}
