package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageEstoqueController {

    private final EstoqueRepository estoqueRepository;

    public PageEstoqueController(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @GetMapping("/estoque")
    public String estoque(HttpSession session, Model model) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        List<EstoqueEntity> estoques = estoqueRepository.findAll();

        model.addAttribute("estoques", estoques);

        return "estoque";
    }
}