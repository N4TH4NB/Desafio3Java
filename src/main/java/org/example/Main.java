package org.example;

import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SistemaExames sistema = new SistemaExames();
        Scanner scanner = new Scanner(System.in);

        // Adicionando alguns usuários para teste
        Usuario admin = Administrador.criarUsuario("admin", "123", "administrador", "");
        Usuario funcionario = Administrador.criarUsuario("Maria", "xyz", "funcionario", "");

        sistema.adicionarUsuario(admin);
        sistema.adicionarUsuario(funcionario);

        while (true) {
            Usuario usuarioAutenticado = null;

            while (usuarioAutenticado == null) {
                System.out.println("Login");
                System.out.println("Nome:");
                String nome = scanner.nextLine();
                System.out.println("Senha:");
                String senha = scanner.nextLine();
                usuarioAutenticado = sistema.autenticarUsuario(nome, senha);
                if (usuarioAutenticado != null) {
                    System.out.println("Usuário autenticado: " + usuarioAutenticado.getNome());
                } else {
                    System.out.println("Falha na autenticação. Tente novamente.");
                }
            }

            while (true) {
                System.out.println("1. Logout");
                if (usuarioAutenticado instanceof Administrador) {
                    System.out.println("2. Adicionar Usuario");
                }
                if (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador) {
                    System.out.println("3. Criar Requisicao de Exame");
                    System.out.println("4. Registrar Coleta");
                    System.out.println("5. Registrar Diagnostico");
                }
                System.out.println("6. Gerar Relatorio de Exames");
                System.out.println("7. Gerar Relatorio Estatistico");
                System.out.println("8. Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine();  // Consumir a nova linha

                if (opcao == 1) {
                    System.out.println("Logout realizado.");
                    break; // Sair do loop interno para voltar ao login
                } else if (opcao == 2 && usuarioAutenticado instanceof Administrador) {
                    System.out.println("Nome:");
                    String nome = scanner.nextLine();
                    System.out.println("Senha:");
                    String senha = scanner.nextLine();
                    System.out.println("Papel (paciente/funcionario/administrador):");
                    String papel = scanner.nextLine();
                    String cpf = "";
                    if (papel.equals("paciente")) {
                        System.out.println("Cpf:");
                        cpf = scanner.nextLine();
                    }
                    Usuario novoUsuario = Administrador.criarUsuario(nome, senha, papel, cpf);
                    if (novoUsuario != null) {
                        sistema.adicionarUsuario(novoUsuario);
                        System.out.println("Usuário adicionado.");
                    } else {
                        System.out.println("Papel inválido.");
                    }
                } else if (opcao == 3 && (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador)) {
                    System.out.println("ID da Requisicao:");
                    String id = scanner.nextLine();
                    System.out.println("Nome do Paciente:");
                    String nomePaciente = scanner.nextLine();
                    System.out.println("Nome do Medico Solicitante:");
                    String medicoSolicitante = scanner.nextLine();
                    Usuario paciente = sistema.getUsuarios().stream().filter(u -> u.getNome().equals(nomePaciente) && u instanceof Paciente).findFirst().orElse(null);
                    if (paciente != null) {
                        Requisicao requisicao = new Requisicao(id, (Paciente) paciente, medicoSolicitante);
                        System.out.println("Tipo de Exame:");
                        String tipo = scanner.nextLine();
                        Exame exame = new Exame(tipo, "", 0, "", "");
                        requisicao.adicionarExame(exame);
                        sistema.adicionarRequisicao(requisicao);
                        System.out.println("Requisicao criada e exame adicionado.");
                    } else {
                        System.out.println("Paciente não encontrado.");
                    }
                } else if (opcao == 4 && (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador)) {
                    System.out.println("ID da Requisicao:");
                    String id = scanner.nextLine();
                    Requisicao requisicao = sistema.gerarRelatorioExames().stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
                    if (requisicao != null) {
                        System.out.println("Tipo de Exame:");
                        String tipo = scanner.nextLine();
                        Exame exame = requisicao.getExames().stream().filter(e -> e.getTipo().equals(tipo)).findFirst().orElse(null);
                        if (exame != null) {
                            System.out.println("Material:");
                            String material = scanner.nextLine();
                            System.out.println("Quantidade:");
                            double quantidade = scanner.nextDouble();
                            scanner.nextLine();  // Consumir a nova linha
                            System.out.println("Hora da Coleta:");
                            String hora = scanner.nextLine();
                            ((Funcionario) usuarioAutenticado).registrarColeta(exame, material, quantidade, hora);
                            System.out.println("Coleta registrada.");
                        } else {
                            System.out.println("Exame não encontrado.");
                        }
                    } else {
                        System.out.println("Requisição não encontrada.");
                    }
                } else if (opcao == 5 && (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador)) {
                    System.out.println("ID da Requisicao:");
                    String id = scanner.nextLine();
                    Requisicao requisicao = sistema.gerarRelatorioExames().stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
                    if (requisicao != null) {
                        System.out.println("Tipo de Exame:");
                        String tipo = scanner.nextLine();
                        Exame exame = requisicao.getExames().stream().filter(e -> e.getTipo().equals(tipo)).findFirst().orElse(null);
                        if (exame != null) {
                            System.out.println("Diagnostico:");
                            String diagnostico = scanner.nextLine();
                            ((Funcionario) usuarioAutenticado).registrarDiagnostico(exame, diagnostico);
                            System.out.println("Diagnostico registrado.");
                        } else {
                            System.out.println("Exame não encontrado.");
                        }
                    } else {
                        System.out.println("Requisição não encontrada.");
                    }
                } else if (opcao == 6) {
                    List<Requisicao> relatorio = sistema.gerarRelatorioExames();
                    for (Requisicao requisicao : relatorio) {
                        System.out.println(requisicao);
                        for (Exame exame : requisicao.getExames()) {
                            System.out.println("  Exame: " + exame.getTipo() + ", Material: " + exame.getMaterial() + ", Quantidade: " + exame.getQuantidade() + ", Hora Coleta: " + exame.getHoraColeta() + ", Diagnostico: " + exame.getDiagnostico());
                        }
                    }
                } else if (opcao == 7) {
                    List<Exame> relatorioEstatistico = sistema.gerarRelatorioEstatistico();
                    Map<String, Integer> contagemExames = new HashMap<>();
                    for (Exame exame : relatorioEstatistico) {
                        contagemExames.put(exame.getTipo(), contagemExames.getOrDefault(exame.getTipo(), 0) + 1);
                    }
                    for (Map.Entry<String, Integer> entry : contagemExames.entrySet()) {
                        System.out.println("Tipo: " + entry.getKey() + ", Quantidade: " + entry.getValue());
                    }
                } else if (opcao == 8) {
                    System.out.println("Saindo do sistema.");
                    System.exit(0); // Encerrar a aplicação
                } else {
                    System.out.println("Opção inválida.");
                }
            }
        }
    }
}
