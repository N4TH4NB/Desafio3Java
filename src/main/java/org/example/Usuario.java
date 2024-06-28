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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getNome() {
        return nome;
    }

    public String getPapel() {
        return papel;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}

class Paciente extends Usuario {
    private String cpf;

    public Paciente(String nome, String senha, String cpf) {
        super(nome, senha, "paciente");
        this.setCpf(cpf);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}

class Funcionario extends Usuario {
    public Funcionario(String nome, String senha) {
        super(nome, senha, "funcionario");
    }

    public void registrarColeta(Exame exame, String material, double quantidade, String hora) {
        exame.setMaterial(material);
        exame.setQuantidade(quantidade);
        exame.setHoraColeta(hora);
    }

    public void registrarDiagnostico(Exame exame, String diagnostico) {
        exame.setDiagnostico(diagnostico);
    }
}

class Administrador extends Funcionario {

    public Administrador(String nome, String senha) {
        super(nome, senha);
        this.setPapel("administrador");
    }

    public Usuario criarUsuario(String nome, String senha, String papel, String cpf) {
        return switch (papel) {
            case "paciente" -> new Paciente(nome, senha, cpf);
            case "funcionario" -> new Funcionario(nome, senha);
            case "administrador" -> new Administrador(nome, senha);
            default -> null;
        };
    }
}



