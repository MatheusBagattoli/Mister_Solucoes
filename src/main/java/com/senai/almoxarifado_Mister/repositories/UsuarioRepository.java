package com.senai.almoxarifado_Mister.repositories;

import com.senai.almoxarifado_Mister.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmailAndSenha(String email, String senha);

    boolean existsByCpf(String cpf);

}
