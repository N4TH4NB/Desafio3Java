package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaExames sistema = new SistemaExames();
        Scanner scanner = new Scanner(System.in);

        Usuario admin = Administrador.criarUsuario("admin", "123", "administrador", "");
        Usuario paciente1 = Administrador.criarUsuario("Joao", "abc", "paciente", "12345678900");
        Usuario paciente2 = Administrador.criarUsuario("Maria", "def", "paciente", "23456789011");
        Usuario funcionario1 = Administrador.criarUsuario("Carlos", "ghi", "funcionario", "");
        Usuario funcionario2 = Administrador.criarUsuario("Ana", "jkl", "funcionario", "");

        sistema.adicionarUsuario(admin);
        sistema.adicionarUsuario(paciente1);
        sistema.adicionarUsuario(paciente2);
        sistema.adicionarUsuario(funcionario1);
        sistema.adicionarUsuario(funcionario2);

        // Adicionando algumas requisições de exames para teste
        Requisicao requisicao1 = new Requisicao("001", (Paciente) paciente1, "Dr. Silva");
        Exame exame1 = new Exame("Colesterol", "Sangue", 5.0, "14:30", "");
        requisicao1.adicionarExame(exame1);
        sistema.adicionarRequisicao(requisicao1);

        Requisicao requisicao2 = new Requisicao("002", (Paciente) paciente2, "Dr. Souza");
        Exame exame2 = new Exame("Diabetes", "", 0, "", "");
        requisicao2.adicionarExame(exame2);
        sistema.adicionarRequisicao(requisicao2);

        Requisicao requisicao3 = new Requisicao("003", (Paciente) paciente2, "Dr. Lima");
        Exame exame3 = new Exame("Raio-X", "Raio-X", 0, "10:55", "Perna Quebrada");
        requisicao3.adicionarExame(exame3);
        sistema.adicionarRequisicao(requisicao3);

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

                System.out.println("2. Gerar Relatorio de Exames");
                System.out.println("3. Gerar Relatorio Estatistico");
                System.out.println("4. Gerar Relatorio por Periodo");

                if (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador) {
                    System.out.println("5. Criar Requisicao de Exame");
                    System.out.println("6. Registrar Coleta");
                    System.out.println("7. Registrar Diagnostico");
                }

                if (usuarioAutenticado instanceof Administrador) {
                    System.out.println("8. Adicionar Usuario");
                }

                System.out.println("9. Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 1) {
                    System.out.println("Logout realizado.");
                    break;
                } else if (opcao == 2) {
                    List<Requisicao> relatorio = sistema.gerarRelatorioExames();
                    if (usuarioAutenticado instanceof Paciente) {
                        for (Requisicao requisicao : relatorio) {
                            if (requisicao.getPaciente().getNome().equals(usuarioAutenticado.getNome())) {
                                System.out.println(requisicao);
                                for (Exame exame : requisicao.getExames()) {
                                    System.out.println("  Exame: " + exame.getTipo() + ", Material: " + exame.getMaterial() + ", Quantidade: " + exame.getQuantidade() + ", Hora Coleta: " + exame.getHoraColeta() + ", Diagnostico: " + exame.getDiagnostico());
                                }
                            }
                        }
                    } else {
                        for (Requisicao requisicao : relatorio) {
                            System.out.println(requisicao);
                            for (Exame exame : requisicao.getExames()) {
                                System.out.println("  Exame: " + exame.getTipo() + ", Material: " + exame.getMaterial() + ", Quantidade: " + exame.getQuantidade() + ", Hora Coleta: " + exame.getHoraColeta() + ", Diagnostico: " + exame.getDiagnostico());
                            }
                        }
                    }
                } else if (opcao == 3) {
                    List<Exame> relatorioEstatistico;
                    Map<String, Integer> contagemExames = new HashMap<>();
                    if (usuarioAutenticado instanceof Paciente) {
                        relatorioEstatistico = sistema.gerarRelatorioEstatistico(usuarioAutenticado.getNome());
                        for (Exame exame : relatorioEstatistico) {
                            contagemExames.put(exame.getTipo(), contagemExames.getOrDefault(exame.getTipo(), 0) + 1);
                        }
                    }else {
                        relatorioEstatistico = sistema.gerarRelatorioEstatistico(null);
                        for (Exame exame : relatorioEstatistico) {
                        contagemExames.put(exame.getTipo(), contagemExames.getOrDefault(exame.getTipo(), 0) + 1);
                        }
                    }
                    for (Map.Entry<String, Integer> entry : contagemExames.entrySet()) {
                        System.out.println("Tipo: " + entry.getKey() + ", Quantidade: " + entry.getValue());
                    }
                } else if (opcao == 4) {
                    System.out.println("Hora de Inicio:");
                    String inicio = scanner.nextLine();
                    System.out.println("Hora de Fim:");
                    String fim = scanner.nextLine();
                    List<Exame> relatorioPorPeriodo;
                    if (usuarioAutenticado instanceof Paciente) {
                        relatorioPorPeriodo = sistema.gerarRelatorioPorPeriodo(inicio, fim, usuarioAutenticado.getNome());
                    } else {
                        relatorioPorPeriodo = sistema.gerarRelatorioPorPeriodo(inicio, fim, null);
                    }
                    for (Exame exame : relatorioPorPeriodo) {
                        System.out.println("  Hora Coleta: " + exame.getHoraColeta() + ", Exame: " + exame.getTipo() + ", Material: " + exame.getMaterial() + ", Quantidade: " + exame.getQuantidade() + ", Diagnostico: " + exame.getDiagnostico());
                    }
                } else if (opcao == 5 && (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador)) {
                    System.out.println("ID da Requisicao:");
                    String id = scanner.nextLine();
                    System.out.println("Nome do Paciente:");
                    String nomePaciente = scanner.nextLine();
                    System.out.println("Nome do Medico Solicitante:");
                    String medicoSolicitante = scanner.nextLine();
                    Usuario paciente = sistema.getUsuarios().stream().filter(u -> u.getNome().equals(nomePaciente) && u instanceof Paciente).findFirst().orElse(null);
                    if (paciente != null) {
                        Requisicao requisicao = new Requisicao(id, (Paciente) paciente, medicoSolicitante);
                        System.out.println("Quantidade de Exames:");
                        int qnt = scanner.nextInt();
                        scanner.nextLine();
                        for (int i = 0; i < qnt; i++) {
                            System.out.println("Tipo de Exame:");
                            String tipo = scanner.nextLine();
                            Exame exame = new Exame(tipo, "", 0, "", "");
                            requisicao.adicionarExame(exame);
                        }
                        System.out.println("Senha para confirmação:");
                        String senha = scanner.nextLine();
                        usuarioAutenticado.autenticar(senha);
                        if (usuarioAutenticado.autenticar(senha)) {
                            sistema.adicionarRequisicao(requisicao);
                            System.out.println("Requisicao criada e exame adicionado.");
                        } else {
                            System.out.println("Senha invalida, realize o processo novamente!");
                            opcao = 5;
                        }
                    } else {
                        System.out.println("Paciente não encontrado.");
                    }
                } else if (opcao == 6 && (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador)) {
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
                            System.out.println("Senha para confirmação:");
                            String senha = scanner.nextLine();
                            if (usuarioAutenticado.autenticar(senha)) {
                                ((Funcionario) usuarioAutenticado).registrarColeta(exame, material, quantidade, hora);
                                System.out.println("Coleta registrada.");
                            } else {
                                System.out.println("Senha invalida, realize o processo novamente!");
                                opcao = 6;
                            }
                        } else {
                            System.out.println("Exame não encontrado.");
                        }
                    } else {
                        System.out.println("Requisição não encontrada.");
                    }
                } else if (opcao == 7 && (usuarioAutenticado instanceof Funcionario || usuarioAutenticado instanceof Administrador)) {
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
                            System.out.println("Senha para confirmação:");
                            String senha = scanner.nextLine();
                            if (usuarioAutenticado.autenticar(senha)) {
                                ((Funcionario) usuarioAutenticado).registrarDiagnostico(exame, diagnostico);
                                System.out.println("Diagnostico registrado.");
                            } else {
                                System.out.println("Senha invalida, realize o processo novamente!");
                                opcao = 7;
                            }
                        } else {
                            System.out.println("Exame não encontrado.");
                        }
                    } else {
                        System.out.println("Requisição não encontrada.");
                    }
                } else if (opcao == 8 && usuarioAutenticado instanceof Administrador) {
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

                } else if (opcao == 9) {
                    System.out.println("Saindo do sistema.");
                    System.exit(0);
                } else {
                    System.out.println("Opção inválida.");
                }
            }
        }
    }
}
