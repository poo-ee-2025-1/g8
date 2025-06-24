package edu.grupo8.models;

import java.time.LocalDate;

public class ManutencaoAgenda {
    private String nome;
    private LocalDate data;

    public ManutencaoAgenda(String nome, LocalDate data) {
        this.nome = nome;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getData() {
        return data;
    }
}
