package edu.grupo8;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.grupo8.components.ItemLine;
import edu.grupo8.models.Equipamento;
import edu.grupo8.models.Manutencao;
import edu.grupo8.models.Manutencao.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class ManutencoesController implements Initializable{
    @FXML
    StackPane listStackPane;

    Manutencao manutencao = new Manutencao("Limpeza De Dutos", Status.CONCLUIDO, LocalDate.of(2025, 07, 4));
    Equipamento equipamento = new Equipamento("Dutos De Ventilação", "Dutos de ventilação do Juninho Motopeças. Chave de fenda e Philips");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listStackPane.getChildren().add(new ItemLine(equipamento));
    }
}
