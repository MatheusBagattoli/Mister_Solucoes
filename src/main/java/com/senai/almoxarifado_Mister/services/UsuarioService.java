package com.senai.almoxarifado_Mister.services;

import com.senai.almoxarifado_Mister.entities.UsuarioEntity;
import com.senai.almoxarifado_Mister.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity realizarLogin(String email, String senha) {

        return usuarioRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() -> new RuntimeException("Usuário ou senha incorreta!"));
    }

    public UsuarioEntity cadastrar(UsuarioEntity usuario) {

        // Verifica se o CPF já existe
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new RuntimeException("CPF já cadastrado!");
        }

        // Salva o usuário
        return usuarioRepository.save(usuario);
    }
}