package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
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

        // Verifica se o usuário está logado
        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        // Busca todos os estoques
        List<EstoqueEntity> estoques = estoqueRepository.findAll();

        // Envia os estoques para o HTML
        model.addAttribute("estoques", estoques);

        // Envia o usuário logado para o HTML
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "estoque";
    }
}