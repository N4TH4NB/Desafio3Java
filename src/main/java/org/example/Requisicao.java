package org.example;

import java.util.ArrayList;
import java.util.List;

class Requisicao {
    private String id;
    private Paciente paciente;
    private String medicoSolicitante;
    private final List<Exame> exames;

    Requisicao(String id, Paciente paciente, String medicoSolicitante) {
        this.setId(id);
        this.setPaciente(paciente);
        this.setMedicoSolicitante(medicoSolicitante);
        this.exames = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedicoSolicitante(String medicoSolicitante) {
        this.medicoSolicitante = medicoSolicitante;
    }

    public void adicionarExame(Exame exame) {
        exames.add(exame);
    }

    public String getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getMedicoSolicitante() {
        return medicoSolicitante;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public String toString(){
        return "Id: " + id + " | Paciente: " + paciente.getNome() + " | Medico Solicitante: " + medicoSolicitante;
    }
}
