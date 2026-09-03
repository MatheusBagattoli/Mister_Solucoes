package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.entities.ProdutoEntity;
import com.senai.almoxarifado_Mister.repositories.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        return "redirect:/produto/cadastrar";
    }
}