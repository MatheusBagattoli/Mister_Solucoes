package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.services.EstoqueService;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping("/estoque/entrada")
    public String entrada(
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            HttpSession session) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        // Precisa estar logado
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        // Somente ADM pode fazer entrada
        if (!"ADM".equals(usuarioLogado.getPerfil())) {
            return "redirect:/estoque?erro=semPermissao";
        }

        try {

            estoqueService.entrada(produtoId, quantidade, usuarioLogado.getNome());

            return "redirect:/estoque?sucesso";

        } catch (RuntimeException e) {

            return "redirect:/estoque?erro=quantidade";
        }
    }


    @PostMapping("/estoque/saida")
    public String saida(
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            HttpSession session) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        // Precisa estar logado
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        // Somente ADM pode fazer saída
        if (!"ADM".equals(usuarioLogado.getPerfil())) {
            return "redirect:/estoque?erro=semPermissao";
        }

        try {

            estoqueService.saida(produtoId, quantidade, usuarioLogado.getNome());

            return "redirect:/estoque?sucesso";

        } catch (RuntimeException e) {

            return "redirect:/estoque?erro=estoque";
        }
    }
}