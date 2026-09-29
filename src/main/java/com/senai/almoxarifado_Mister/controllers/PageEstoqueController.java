package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;
import com.senai.almoxarifado_Mister.repositories.EstoqueRepository;
import com.senai.almoxarifado_Mister.services.EstoqueService;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageEstoqueController {

    private final EstoqueRepository estoqueRepository;
    private final EstoqueService estoqueService;

    public PageEstoqueController(
            EstoqueRepository estoqueRepository,
            EstoqueService estoqueService) {

        this.estoqueRepository = estoqueRepository;
        this.estoqueService = estoqueService;
    }

    @GetMapping("/estoque")
    public String estoque(@RequestParam(defaultValue = "nome") String ordenarPor, HttpSession session, Model model) {

        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        List<EstoqueEntity> estoques = estoqueRepository.findAll();

        if (!ordenarPor.equals("nome") && !ordenarPor.equals("quantidade") && !ordenarPor.equals("estoqueMinimo")) {
            ordenarPor = "nome";
        }

        estoqueService.ordenar(estoques, ordenarPor);

        model.addAttribute("estoques", estoques);

        model.addAttribute("totalEstoqueBaixo", estoqueService.contarEstoqueBaixo(estoques));

        model.addAttribute("totalSemEstoque", estoqueService.contarSemEstoque(estoques));

        model.addAttribute("totalProdutos", estoques.size());

        model.addAttribute("ordenarPor", ordenarPor);

        model.addAttribute("usuarioLogado", usuarioLogado);

        return "estoque";
    }
}
