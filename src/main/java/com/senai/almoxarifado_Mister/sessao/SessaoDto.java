package com.senai.almoxarifado_Mister.sessao;

public class SessaoDto {

    private Long id;
    private String nome;
    private String email;
    private String matricula;
    private String perfil;

    public SessaoDto() {}

    public SessaoDto(Long id, String nome, String email, String matricula, String perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}