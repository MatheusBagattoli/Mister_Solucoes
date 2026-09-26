package com.senai.almoxarifado_Mister.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "produto_id", nullable = false)
        private ProdutoEntity produto;

        @Column(nullable = false, length = 10)
        private String tipo;

        @Column(nullable = false)
        private Integer quantidade;

        @Column(name = "quantidade_anterior", nullable = false)
        private Integer quantidadeAnterior;

        @Column(name = "quantidade_atual", nullable = false)
        private Integer quantidadeAtual;

        @Column(name = "usuario_nome", nullable = false)
        private String usuarioNome;

        @Column(name = "data_hora", nullable = false)
        private LocalDateTime dataHora;

    public MovimentacaoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidadeAnterior() {
        return quantidadeAnterior;
    }

    public void setQuantidadeAnterior(Integer quantidadeAnterior) {
        this.quantidadeAnterior = quantidadeAnterior;
    }

    public Integer getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(Integer quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
