package org.example;

public class Main {
    public static void main(String[] args) {

        SistemaExames sistema = new SistemaExames();
        Usuario admin = new Administrador("admin", "admin123");
        sistema.adicionarUsuario(admin);

        Usuario novoPaciente = Administrador.criarUsuario("João", "joao123", "paciente", "12345678900");
        sistema.adicionarUsuario(novoPaciente);
        System.out.print(sistema.getUsuarios());

        Requisicao novaRequisicao = new Requisicao("abc", (Paciente) novoPaciente, "Pedro");
        sistema.adicionarRequisicao(novaRequisicao);
        System.out.print(sistema.gerarRelatorioExames());
    }
}