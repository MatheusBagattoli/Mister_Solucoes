package com.senai.almoxarifado_Mister.sessao;

import jakarta.servlet.http.HttpSession;

public final class SessaoUtil {
    private static final String CHAVE_USUARIO_LOGADO = "usuarioLogado";
    private static final String CHAVE_USER_ROLE= "userRole";

    private SessaoUtil() {

    }

    public static void logar(HttpSession session, SessaoDto usuario) {
        session.setAttribute(CHAVE_USUARIO_LOGADO, usuario);
    }

    public static SessaoDto usuarioLogado(HttpSession session) {
        return (SessaoDto) session.getAttribute(CHAVE_USUARIO_LOGADO);
    }

    public static SessaoDto userRole(HttpSession session) {
        return (SessaoDto) session.getAttribute(CHAVE_USER_ROLE);
    }

    public static void deslogar(HttpSession session) {
        session.invalidate();
    }
}
