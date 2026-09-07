package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.entities.ProdutoEntity;
import com.senai.almoxarifado_Mister.repositories.ProdutoRepository;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageProdutoController {

    private final ProdutoRepository produtoRepository;

    public PageProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/produto/cadastro")
    public String cadastrarProduto(HttpSession session, Model model) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        model.addAttribute("produto", new ProdutoEntity());

        model.addAttribute("produtos", produtoRepository.findAll());

        model.addAttribute("editando", false);

        return "produto-cadastro";
    }

    @GetMapping("/produto/editar/{id}")
    public String editarProduto(@PathVariable Long id, HttpSession session, Model model) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        ProdutoEntity produto = produtoRepository.findById(id).orElse(null);

        if (produto == null) {
            return "redirect:/produto/cadastro";
        }

        model.addAttribute("produto", produto);

        model.addAttribute("produtos", produtoRepository.findAll());

        model.addAttribute("editando", true);

        return "produto-cadastro";
    }
}