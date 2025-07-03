package edu.grupo8.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Equipamento")
public class Equipamento {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(dataType = DataType.STRING)
    private String nome;

    @DatabaseField(dataType = DataType.STRING)
    private String descricao;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
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

    public int getId() { return id; }
}
