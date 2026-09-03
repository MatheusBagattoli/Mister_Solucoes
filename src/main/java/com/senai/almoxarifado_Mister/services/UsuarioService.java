package com.senai.almoxarifado_Mister.services;

import com.senai.almoxarifado_Mister.dto.UsuarioLoginDto;
import com.senai.almoxarifado_Mister.entities.UsuarioEntity;
import com.senai.almoxarifado_Mister.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity realizarLogin(UsuarioLoginDto login) {
        return usuarioRepository.findByEmailAndSenha(login.email(), login.senha())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha incorreta!"));
    }
}
