package org.example;

import java.util.ArrayList;
import java.util.List;

class Requisicao {
    private final List<Exame> exames;
    private String id;
    private Paciente paciente;
    private String medicoSolicitante;

    Requisicao(String id, Paciente paciente, String medicoSolicitante) {
        this.setId(id);
        this.setPaciente(paciente);
        this.setMedicoSolicitante(medicoSolicitante);
        this.exames = new ArrayList<>();
    }

    public void adicionarExame(Exame exame) {
        exames.add(exame);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getMedicoSolicitante() {
        return medicoSolicitante;
    }

    public void setMedicoSolicitante(String medicoSolicitante) {
        this.medicoSolicitante = medicoSolicitante;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public String toString() {
        return "Id: " + id + " | Paciente: " + paciente.getNome() + " | Medico Solicitante: " + medicoSolicitante;
    }
}
