package org.example;

public interface Registros {
    default void registrarColeta(Exame exame, String material, double quantidade, String hora) {
        exame.setMaterial(material);
        exame.setQuantidade(quantidade);
        exame.setHoraColeta(hora);
    }

    default void registrarDiagnostico(Exame exame, String diagnostico) {
        exame.setDiagnostico(diagnostico);
    }
}
