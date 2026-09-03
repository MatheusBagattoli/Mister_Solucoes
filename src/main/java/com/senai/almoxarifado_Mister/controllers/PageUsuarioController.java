package com.senai.almoxarifado_Mister.controllers;

import com.senai.almoxarifado_Mister.sessao.SessaoDto;
import com.senai.almoxarifado_Mister.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageUsuarioController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        SessaoDto usuarioLogado = SessaoUtil.usuarioLogado(session);
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessaoUtil.deslogar(session);
        return "redirect:/login";
    }
}
