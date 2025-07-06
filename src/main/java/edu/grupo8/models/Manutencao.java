package edu.grupo8.models;

import java.time.LocalDate;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Manutencao")
public class Manutencao {

    public enum Status {
            CONCLUIDO, PENDENTE, ATRASADO, CANCELADO;
        }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private Status status;

    @DatabaseField(dataType = DataType.STRING)
    private String nome;

    @DatabaseField(dataType = DataType.STRING)
    private String data;

    // Atributo para fazer a relação 1:N via chave estrangeira pelo ORMLite
    @ForeignCollectionField(eager = true) // ← esta é a anotação correta
    private ForeignCollection<Equipamento> equipamentos;

    public Manutencao() {}

    public Manutencao(String nome, Status status, LocalDate data) {
        this.nome = nome;
        this.status = status;
        setData(data);
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
