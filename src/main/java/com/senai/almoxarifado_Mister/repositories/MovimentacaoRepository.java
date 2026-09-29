package com.senai.almoxarifado_Mister.repositories;

import com.senai.almoxarifado_Mister.entities.MovimentacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {

    List<MovimentacaoEntity> findAllByOrderByDataHoraDesc();

    List<MovimentacaoEntity> findByProdutoIdOrderByDataHoraDesc(Long produtoId);

    List<MovimentacaoEntity> findByTipoOrderByDataHoraDesc(String tipo);

    boolean existsByProdutoId(Long produtoId);
}
