package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.dto.UsuarioLoginDto;
import com.senai.almoxarifado_Mister.entities.UsuarioEntity;
import com.senai.almoxarifado_Mister.services.UsuarioService;
import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsuarioLoginDto dados, HttpSession session) {

        try {
            UsuarioEntity usuario = usuarioService.realizarLogin(dados.email(), dados.senha());

            SessaoUtil.logar(session, new SessaoDto(usuario.getId(), usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getMatricula(),
                    usuario.getPerfil()));

            return "redirect:/home";

        } catch (RuntimeException e) {

            return "redirect:/login?erro=login";
        }
    }

    @PostMapping("/usuario/cadastrar")
    public String cadastrar(UsuarioEntity usuario) {

        try {

            // Define o perfil padrão
            if (usuario.getPerfil() == null || usuario.getPerfil().isBlank()) {
                usuario.setPerfil("FUNCIONARIO");
            }

            usuarioService.cadastrar(usuario);

            return "redirect:/usuario/cadastro?sucesso";

        } catch (RuntimeException e) {

            return "redirect:/usuario/cadastro?erro=cpf";
        }
    }
}