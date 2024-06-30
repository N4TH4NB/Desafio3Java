package org.example;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SistemaExames sistema = new SistemaExames();
        Administrador admin = new Administrador("admin", "admin123");
        sistema.adicionarUsuario(admin);

        Usuario novoPaciente = Administrador.criarUsuario("João", "joao123", "paciente", "12345678900");
        sistema.adicionarUsuario(novoPaciente);
        System.out.print(sistema.getUsuarios());

    }
}