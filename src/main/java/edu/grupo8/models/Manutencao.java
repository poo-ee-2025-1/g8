package edu.grupo8.models;

import java.time.LocalDate;

import com.j256.ormlite.dao.ForeignCollection;

public class Manutencao {
    public enum Status {
            CONCLUIDO, PENDENTE, ATRASADO, CANCELADO;
        }
    
    private Status status;
    private String nome;
    private int id;
    private String data;

    // Atributo para fazer a relação 1:N via chave estrangeira pelo ORMLite
    private ForeignCollection<Equipamento> equipamentos;

    public Manutencao() {}

    public Manutencao(String nome, Status status, LocalDate data) {
        this.nome = nome;
        this.status = status;
        setData(data);;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getData() {
        return LocalDate.parse(data);
    }

    public void setData(LocalDate data) {
        this.data = data.toString();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
