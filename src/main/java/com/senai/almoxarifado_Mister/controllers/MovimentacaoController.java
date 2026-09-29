package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.entities.MovimentacaoEntity;
import com.senai.almoxarifado_Mister.repositories.MovimentacaoRepository;
import com.senai.almoxarifado_Mister.repositories.ProdutoRepository;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MovimentacaoController {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentacaoController(
            MovimentacaoRepository movimentacaoRepository,
            ProdutoRepository produtoRepository) {

        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/historico-estoque")
    public String historico(
            @RequestParam(required = false) Long produtoId,
            @RequestParam(required = false) String tipo,
            HttpSession session,
            Model model) {

        SessaoDto usuarioLogado =
                SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        List<MovimentacaoEntity> movimentacoes;

        if (produtoId != null) {

            movimentacoes = movimentacaoRepository.findByProdutoIdOrderByDataHoraDesc(produtoId);

        } else if (tipo != null && !tipo.isBlank()) {

            movimentacoes = movimentacaoRepository.findByTipoOrderByDataHoraDesc(tipo);

        } else {

            movimentacoes = movimentacaoRepository.findAllByOrderByDataHoraDesc();
        }

        model.addAttribute("movimentacoes", movimentacoes);

        model.addAttribute("produtos", produtoRepository.findAll());

        model.addAttribute("produtoSelecionado", produtoId);

        model.addAttribute("tipoSelecionado", tipo);

        model.addAttribute("usuarioLogado", usuarioLogado);
        return "historico-estoque";
    }
}

