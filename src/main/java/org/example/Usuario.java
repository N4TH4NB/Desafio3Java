package org.example;

abstract class Usuario {
    private String nome;
    private String senha;
    private String papel;

    Usuario(String nome, String senha, String papel) {
        this.setNome(nome);
        this.setSenha(senha);
        this.setPapel(papel);
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    public String toString() {
        return nome + papel;
    }
}

class Paciente extends Usuario {
    private String cpf;

    Paciente(String nome, String senha, String cpf) {
        super(nome, senha, "paciente");
        this.setCpf(cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

class Funcionario extends Usuario implements Registros {
    public Funcionario(String nome, String senha) {
        super(nome, senha, "funcionario");
    }
}

class Administrador extends Funcionario {

    public Administrador(String nome, String senha) {
        super(nome, senha);
        this.setPapel("administrador");
    }

    public static Usuario criarUsuario(String nome, String senha, String papel, String cpf) {
        return switch (papel) {
            case "paciente" -> new Paciente(nome, senha, cpf);
            case "funcionario" -> new Funcionario(nome, senha);
            case "administrador" -> new Administrador(nome, senha);
            default -> null;
        };
    }
}