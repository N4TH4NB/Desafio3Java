package org.example;

import java.util.ArrayList;
import java.util.List;

public class SistemaExames {
    private final List<Usuario> usuarios;
    private final List<Requisicao> requisicoes;

    SistemaExames() {
        usuarios = new ArrayList<>();
        requisicoes = new ArrayList<>();
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario autenticarUsuario(String nome, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome) && usuario.autenticar(senha)) {
                return usuario;
            }
        }
        return null;
    }

    public void adicionarRequisicao(Requisicao requisicao) {
        requisicoes.add(requisicao);
    }

    public List<Requisicao> gerarRelatorioExames() {
        return requisicoes;
    }

    public List<Exame> gerarRelatorioEstatistico() {
        List<Exame> exames = new ArrayList<>();
        for (Requisicao requisicao : requisicoes) {
            exames.addAll(requisicao.getExames());
        }
        return exames;
    }
}
