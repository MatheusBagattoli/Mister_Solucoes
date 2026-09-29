package com.senai.almoxarifado_Mister.services;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.entities.MovimentacaoEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import com.senai.almoxarifado_Mister.repositories.MovimentacaoRepository;
import com.senai.almoxarifado_Mister.sessao.AlgoritmoOrdenacao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto."));
    }

    @Transactional
    public EstoqueEntity entrada(Long produtoId, Integer quantidade, String usuarioNome) {

        validarQuantidade(quantidade);

        EstoqueEntity estoque = buscarPorProduto(produtoId);

        int quantidadeAnterior = estoque.getQuantidade();
        int quantidadeAtual = quantidadeAnterior + quantidade;

        estoque.setQuantidade(quantidadeAtual);

        EstoqueEntity estoqueSalvo = estoqueRepository.save(estoque);

        registrarMovimentacao(estoqueSalvo, "ENTRADA", quantidade, quantidadeAnterior, quantidadeAtual, usuarioNome);
        return estoqueSalvo;
    }

    @Transactional
    public EstoqueEntity saida(Long produtoId, Integer quantidade, String usuarioNome) {
        validarQuantidade(quantidade);

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

    public List<EstoqueEntity> ordenar(List<EstoqueEntity> estoques, String criterio) {

        AlgoritmoOrdenacao.ordenar(estoques, criterio);

        return estoques;
    }

    public long contarEstoqueBaixo(List<EstoqueEntity> estoques) {

        return estoques.stream().filter(estoque -> estoque.getQuantidade() <= estoque.getProduto().getEstoqueMinimo()).count();
    }

    public long contarSemEstoque(List<EstoqueEntity> estoques) {

        return estoques.stream().filter(estoque -> estoque.getQuantidade() == 0).count();
    }

    private void validarQuantidade(Integer quantidade) {

        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }
    }

    private void registrarMovimentacao(
            EstoqueEntity estoque,
            String tipo,
            Integer quantidade,
            Integer quantidadeAnterior,
            Integer quantidadeAtual,
            String usuarioNome) {

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
