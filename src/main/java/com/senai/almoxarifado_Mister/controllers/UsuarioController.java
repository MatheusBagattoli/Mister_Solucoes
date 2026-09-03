package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.dto.UsuarioLoginDto;
import com.senai.almoxarifado_Mister.entities.UsuarioEntity;
import com.senai.almoxarifado_Mister.services.UsuarioService;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public String realizarLogin(UsuarioLoginDto login, Model model, HttpSession session) {
        try {
            UsuarioEntity usuario = usuarioService.realizarLogin(login);
            SessaoUtil.logar(session, new SessaoDto(
                    usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getMatricula()
            ));
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }
}
