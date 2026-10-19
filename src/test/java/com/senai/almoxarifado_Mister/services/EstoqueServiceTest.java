package com.senai.almoxarifado_Mister.services;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.entities.MovimentacaoEntity;
import com.senai.almoxarifado_Mister.entities.ProdutoEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import com.senai.almoxarifado_Mister.repositories.MovimentacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    private ProdutoEntity produto;
    private EstoqueEntity estoque;

    @BeforeEach
    void configurar() {
        produto = new ProdutoEntity();
        produto.setId(1L);
        produto.setNome("Chave de fenda");
        produto.setPreco(15.0);
        produto.setEstoqueMinimo(5);

        estoque = new EstoqueEntity();
        estoque.setId(10L);
        estoque.setProduto(produto);
        estoque.setQuantidade(10);
    }

    @Test
    void deveRegistrarEntradaEAtualizarSaldo() {
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(estoque)).thenReturn(estoque);

        EstoqueEntity resultado = estoqueService.entrada(1L, 5, "Ana");

        assertEquals(15, resultado.getQuantidade());
        verify(estoqueRepository).save(estoque);
        verify(movimentacaoRepository).save(argThat(m ->
                m.getTipo().equals("ENTRADA")
                        && m.getQuantidade() == 5
                        && m.getQuantidadeAnterior() == 10
                        && m.getQuantidadeAtual() == 15
                        && m.getUsuarioNome().equals("Ana")
                        && m.getDataHora() != null));
    }

    @Test
    void naoDeveAceitarEntradaComQuantidadeZero() {
        assertThrows(RuntimeException.class, () -> estoqueService.entrada(1L, 0, "Ana"));
        verifyNoInteractions(estoqueRepository, movimentacaoRepository);
    }

    @Test
    void naoDeveAceitarEntradaComQuantidadeNegativa() {
        assertThrows(RuntimeException.class, () -> estoqueService.entrada(1L, -2, "Ana"));
        verifyNoInteractions(estoqueRepository, movimentacaoRepository);
    }

    @Test
    void deveRegistrarSaidaEAtualizarSaldo() {
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(estoque)).thenReturn(estoque);

        EstoqueEntity resultado = estoqueService.saida(1L, 4, "Carlos");

        assertEquals(6, resultado.getQuantidade());
        verify(movimentacaoRepository).save(argThat(m ->
                m.getTipo().equals("SAÍDA")
                        && m.getQuantidade() == 4
                        && m.getQuantidadeAnterior() == 10
                        && m.getQuantidadeAtual() == 6
                        && m.getUsuarioNome().equals("Carlos")
                        && m.getDataHora() != null));
    }

    @Test
    void naoDevePermitirSaidaMaiorQueSaldo() {
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoque));

        assertThrows(RuntimeException.class, () -> estoqueService.saida(1L, 11, "Carlos"));

        assertEquals(10, estoque.getQuantidade());
        verify(estoqueRepository, never()).save(any());
        verifyNoInteractions(movimentacaoRepository);
    }

    @Test
    void deveFalharQuandoEstoqueDoProdutoNaoExiste() {
        when(estoqueRepository.findByProdutoId(99L)).thenReturn(Optional.empty());

        RuntimeException erro = assertThrows(RuntimeException.class,
                () -> estoqueService.entrada(99L, 1, "Ana"));

        assertEquals("Estoque não encontrado para o produto.", erro.getMessage());
    }

    @Test
    void deveContarEstoqueIgualOuAbaixoDoMinimo() {
        EstoqueEntity abaixo = criarEstoque(2, 5);
        EstoqueEntity noLimite = criarEstoque(5, 5);
        EstoqueEntity acima = criarEstoque(6, 5);

        assertEquals(2, estoqueService.contarEstoqueBaixo(List.of(abaixo, noLimite, acima)));
    }

    @Test
    void deveContarProdutosSemEstoque() {
        EstoqueEntity zerado = criarEstoque(0, 5);
        EstoqueEntity comSaldo = criarEstoque(3, 5);

        assertEquals(1, estoqueService.contarSemEstoque(List.of(zerado, comSaldo)));
    }

    private EstoqueEntity criarEstoque(int quantidade, int minimo) {
        ProdutoEntity p = new ProdutoEntity();
        p.setNome("Ferramenta teste");
        p.setEstoqueMinimo(minimo);
        EstoqueEntity e = new EstoqueEntity();
        e.setProduto(p);
        e.setQuantidade(quantidade);
        return e;
    }
}
