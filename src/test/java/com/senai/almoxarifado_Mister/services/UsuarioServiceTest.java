package com.senai.almoxarifado_Mister.services;

import com.senai.almoxarifado_Mister.entities.UsuarioEntity;
import com.senai.almoxarifado_Mister.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveRealizarLoginComCredenciaisValidas() {
        UsuarioEntity usuario = usuarioValido();
        when(usuarioRepository.findByEmailAndSenha("ana@teste.com", "senha123"))
                .thenReturn(Optional.of(usuario));

        UsuarioEntity resultado = usuarioService.realizarLogin("ana@teste.com", "senha123");

        assertSame(usuario, resultado);
    }

    @Test
    void deveRejeitarLoginComCredenciaisInvalidas() {
        when(usuarioRepository.findByEmailAndSenha("ana@teste.com", "errada"))
                .thenReturn(Optional.empty());

        RuntimeException erro = assertThrows(RuntimeException.class,
                () -> usuarioService.realizarLogin("ana@teste.com", "errada"));

        assertEquals("Usuário ou senha incorreta!", erro.getMessage());
    }

    @Test
    void deveCadastrarUsuarioComCpfAindaNaoUtilizado() {
        UsuarioEntity usuario = usuarioValido();
        when(usuarioRepository.existsByCpf("12345678901")).thenReturn(false);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioEntity resultado = usuarioService.cadastrar(usuario);

        assertSame(usuario, resultado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void naoDeveCadastrarUsuarioComCpfDuplicado() {
        UsuarioEntity usuario = usuarioValido();
        when(usuarioRepository.existsByCpf("12345678901")).thenReturn(true);

        RuntimeException erro = assertThrows(RuntimeException.class,
                () -> usuarioService.cadastrar(usuario));

        assertEquals("CPF já cadastrado!", erro.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    private UsuarioEntity usuarioValido() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Ana Souza");
        usuario.setCpf("12345678901");
        usuario.setEmail("ana@teste.com");
        usuario.setSenha("senha123");
        usuario.setPerfil("FUNCIONARIO");
        usuario.setMatricula("MAT001");
        return usuario;
    }
}
