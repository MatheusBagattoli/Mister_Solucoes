package com.senai.almoxarifado_Mister.services;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.entities.MovimentacaoEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import com.senai.almoxarifado_Mister.repositories.MovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public EstoqueService(
            EstoqueRepository estoqueRepository,
            MovimentacaoRepository movimentacaoRepository) {

        this.estoqueRepository = estoqueRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public EstoqueEntity buscarPorProduto(Long produtoId) {

        return estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() ->
                        new RuntimeException("Estoque não encontrado para o produto."));
    }

    public EstoqueEntity entrada(
            Long produtoId,
            Integer quantidade,
            String usuarioNome) {

        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }

        EstoqueEntity estoque = buscarPorProduto(produtoId);

        int quantidadeAnterior = estoque.getQuantidade();

        int quantidadeAtual = quantidadeAnterior + quantidade;

        estoque.setQuantidade(quantidadeAtual);

        EstoqueEntity estoqueSalvo =
                estoqueRepository.save(estoque);

        registrarMovimentacao(estoqueSalvo, "ENTRADA", quantidade, quantidadeAnterior, quantidadeAtual, usuarioNome);

        return estoqueSalvo;
    }

    public EstoqueEntity saida(Long produtoId, Integer quantidade, String usuarioNome) {

        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }

        EstoqueEntity estoque = buscarPorProduto(produtoId);

        if (quantidade > estoque.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente para realizar a saída.");
        }

        int quantidadeAnterior = estoque.getQuantidade();

        int quantidadeAtual = quantidadeAnterior - quantidade;

        estoque.setQuantidade(quantidadeAtual);

        EstoqueEntity estoqueSalvo = estoqueRepository.save(estoque);

        registrarMovimentacao(estoqueSalvo, "SAÍDA", quantidade, quantidadeAnterior, quantidadeAtual, usuarioNome);

        return estoqueSalvo;
    }

    private void registrarMovimentacao(EstoqueEntity estoque, String tipo, Integer quantidade, Integer quantidadeAnterior, Integer quantidadeAtual, String usuarioNome) {

        MovimentacaoEntity movimentacao = new MovimentacaoEntity();

        movimentacao.setProduto(estoque.getProduto());

        movimentacao.setTipo(tipo);

        movimentacao.setQuantidade(quantidade);

        movimentacao.setQuantidadeAnterior(quantidadeAnterior);

        movimentacao.setQuantidadeAtual(quantidadeAtual);

        movimentacao.setUsuarioNome(usuarioNome);

        movimentacao.setDataHora(LocalDateTime.now());

        movimentacaoRepository.save(movimentacao);
    }
}