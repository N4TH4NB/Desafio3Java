package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaExames sistema = new SistemaExames();
        Scanner scanner = new Scanner(System.in);

        Usuario admin = Administrador.criarUsuario("admin", "123", "administrador", "");
        sistema.adicionarUsuario(admin);

        Usuario usuarioAutenticado = null;

        while (true) {
            if (usuarioAutenticado == null) {
                System.out.println("1. Login");
            } else {
                System.out.println("1. Logout");
            }

            System.out.println("2. Adicionar Usuario");
            System.out.println("3. Criar Requisicao de Exame");
            System.out.println("4. Registrar Coleta");
            System.out.println("5. Registrar Diagnostico");
            System.out.println("6. Gerar Relatorio de Exames");
            System.out.println("7. Gerar Relatorio Estatistico");
            System.out.println("8. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                if (usuarioAutenticado == null) {
                    System.out.println("Nome:");
                    String nome = scanner.nextLine();
                    System.out.println("Senha:");
                    String senha = scanner.nextLine();
                    usuarioAutenticado = sistema.autenticarUsuario(nome, senha);
                    if (usuarioAutenticado != null) {
                        System.out.println("Usuário autenticado: " + usuarioAutenticado.getNome());
                    } else {
                        System.out.println("Falha na autenticação.");
                    }
                } else {
                    usuarioAutenticado = null;
                    System.out.println("Logout realizado.");
                }
            } else if (opcao == 2) {
                if (usuarioAutenticado instanceof Administrador) {
                    System.out.println("Nome:");
                    String nome = scanner.nextLine();
                    System.out.println("Senha:");
                    String senha = scanner.nextLine();
                    System.out.println("Papel (paciente/funcionario/administrador):");
                    String papel = scanner.nextLine();
                    String cpf = papel.equals("paciente") ? scanner.nextLine() : "";
                    Usuario novoUsuario = Administrador.criarUsuario(nome, senha, papel, cpf);
                    if (novoUsuario != null) {
                        sistema.adicionarUsuario(novoUsuario);
                        System.out.println("Usuário adicionado.");
                    } else {
                        System.out.println("Papel inválido.");
                    }
                } else {
                    System.out.println("Acesso negado. Somente administradores podem adicionar usuários.");
                }
            } else if (opcao == 3) {
                if (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador) {
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
                } else {
                    System.out.println("Acesso negado. Somente funcionários podem criar requisições.");
                }
            } else if (opcao == 4) {
                if (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador) {
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
                } else {
                    System.out.println("Acesso negado. Somente funcionários podem registrar coletas.");
                }
            } else if (opcao == 5) {
                if (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador) {
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
                } else {
                    System.out.println("Acesso negado. Somente funcionários podem registrar diagnósticos.");
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
                // Agrupando e contando exames por tipo
                relatorioEstatistico.stream()
                        .collect(java.util.stream.Collectors.groupingBy(Exame::getTipo, java.util.stream.Collectors.counting()))
                        .forEach((tipo, count) -> System.out.println("Tipo: " + tipo + ", Quantidade: " + count));
            } else if (opcao == 8) {
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }
}
