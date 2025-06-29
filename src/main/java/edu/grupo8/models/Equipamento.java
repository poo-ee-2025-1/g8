package edu.grupo8.models;

public class Equipamento {
    private int id;
    private String nome;
    private String descricao;
    private Manutencao manutencao;

    public Equipamento() {}

    public Equipamento(String nome, String desc) {
        this.nome = nome;
        this.descricao = desc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Manutencao getManutencao() {
        return manutencao;
    }

    public void setManutencao(Manutencao manutencao) {
        this.manutencao = manutencao;
    }
}
