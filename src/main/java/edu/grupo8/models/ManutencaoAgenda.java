package edu.grupo8.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;

@DatabaseTable(tableName = "ManutencaoAgenda")
public class ManutencaoAgenda {
    @DatabaseField(dataType = DataType.STRING)
    private String nome;

    @DatabaseField(dataType = DataType.STRING)
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
