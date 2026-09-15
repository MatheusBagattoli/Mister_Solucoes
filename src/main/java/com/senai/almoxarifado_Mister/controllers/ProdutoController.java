package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.entities.ProdutoEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import com.senai.almoxarifado_Mister.repositories.ProdutoRepository;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public ProdutoController(
            ProdutoRepository produtoRepository,
            EstoqueRepository estoqueRepository) {

        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }


    @PostMapping("/produto/cadastrar")
    public String cadastrar(
            ProdutoEntity produto,
            HttpSession session) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        if (!"ADM".equals(usuarioLogado.getPerfil())) {
            return "redirect:/produto/cadastro?erro=semPermissao";
        }

        // Salva o produto
        ProdutoEntity produtoSalvo = produtoRepository.save(produto);

        // Cria o estoque automaticamente com quantidade 0
        EstoqueEntity estoque = new EstoqueEntity();

        estoque.setProduto(produtoSalvo);
        estoque.setQuantidade(0);

        estoqueRepository.save(estoque);

        return "redirect:/produto/cadastro?sucesso";
    }


    @PostMapping("/produto/editar")
    public String editar(
            ProdutoEntity produto,
            HttpSession session) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        if (!"ADM".equals(usuarioLogado.getPerfil())) {
            return "redirect:/produto/cadastro?erro=semPermissao";
        }

        produtoRepository.save(produto);

        return "redirect:/produto/cadastro?sucesso";
    }


    @PostMapping("/produto/excluir/{id}")
    public String excluir(
            @PathVariable Long id,
            HttpSession session) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        if (!"ADM".equals(usuarioLogado.getPerfil())) {
            return "redirect:/produto/cadastro?erro=semPermissao";
        }

        // Primeiro remove o estoque
        estoqueRepository.findByProdutoId(id).ifPresent(estoqueRepository::delete);

        // Depois remove o produto
        produtoRepository.deleteById(id);

        return "redirect:/produto/cadastro?sucesso";
    }
}