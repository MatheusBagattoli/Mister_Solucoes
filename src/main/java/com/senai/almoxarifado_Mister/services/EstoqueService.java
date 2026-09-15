package com.senai.almoxarifado_Mister.services;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public EstoqueEntity buscarPorProduto(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado!"));
    }

    public EstoqueEntity entrada(Long produtoId, Integer quantidade) {

        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero!");
        }

        EstoqueEntity estoque = buscarPorProduto(produtoId);

        estoque.setQuantidade(
                estoque.getQuantidade() + quantidade
        );

        return estoqueRepository.save(estoque);
    }

    public EstoqueEntity saida(Long produtoId, Integer quantidade) {

        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero!");
        }

        EstoqueEntity estoque = buscarPorProduto(produtoId);

        if (quantidade > estoque.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente!");
        }

        estoque.setQuantidade(
                estoque.getQuantidade() - quantidade
        );

        return estoqueRepository.save(estoque);
    }
}