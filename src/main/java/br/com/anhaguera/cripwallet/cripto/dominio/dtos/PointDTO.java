package br.com.anhaguera.cripwallet.cripto.dominio.dtos;

import java.time.LocalDate;
import java.util.Date;

public class PointDTO {
    private LocalDate data;
    private Double valor;
    private Double quantidade;

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
