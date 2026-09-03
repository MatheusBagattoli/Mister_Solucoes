package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageProdutoController {

    @GetMapping("/produto/cadastrar")
    public String cadastrarProduto(HttpSession session) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        return "produto-cadastro";
    }
}