package org.example;

public class Exame {
    private String tipo;
    private String material;
    private double quantidade;
    private String horaColeta;
    private String diagnostico;

    Exame(String tipo, String material, double quantidade, String horaColeta, String diagnostico) {
        this.setTipo(tipo);
        this.setMaterial(material);
        this.setQuantidade(quantidade);
        this.setHoraColeta(horaColeta);
        this.setDiagnostico(diagnostico);
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public void setHoraColeta(String horaColeta) {
        this.horaColeta = horaColeta;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMaterial() {
        return material;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String getHoraColeta() {
        return horaColeta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String toString() {
        return tipo + " | " + material + " | " + quantidade + " | " + horaColeta + " | " + diagnostico;
    }
}
