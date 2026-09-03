package com.senai.almoxarifado_Mister.repositories;

import com.senai.almoxarifado_Mister.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}
