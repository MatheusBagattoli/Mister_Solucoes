package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.entities.ProdutoEntity;
import com.senai.almoxarifado_Mister.repositories.ProdutoRepository;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/cadastrar")
    public String cadastrarProduto(@ModelAttribute ProdutoEntity produto) {

        produtoRepository.save(produto);

        return "redirect:/produto/cadastro";
    }

    @PostMapping("/editar")
    public String salvarEdicao(@Valid @ModelAttribute("produto") ProdutoEntity produto, BindingResult result, HttpSession session) {

        if (SessaoUtil.usuarioLogado(session) == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "produto-cadastro";
        }

        produtoRepository.save(produto);

        return "redirect:/produto/cadastro?editado";
    }

    @PostMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, HttpSession session) {

        if (SessaoUtil.usuarioLogado(session) == null) {
            return "redirect:/login";
        }

        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        }

        return "redirect:/produto/cadastro?excluido";
    }
}