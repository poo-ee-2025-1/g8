package edu.grupo8.components;

import java.sql.SQLException;

import edu.grupo8.models.Equipamento;
import edu.grupo8.utils.EquipamentoDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class CreateEquipamentoWindow extends VBox{
    private Label titulo = new Label("Registrar Equipamento");
    private TextField nome =  new TextField();
    private TextField descricao =  new TextField();
    private StackPane botaoConcluir = new StackPane();
    private StackPane botaoCancelar = new StackPane();
    private Label mensagemLabel = new Label();

    private Popup popup = new Popup();

    public CreateEquipamentoWindow(Popup popup) {
        this.popup = popup;

        config();
        render();
    }

    private void config() {
        setSpacing(20);
        setPadding(new Insets(10));
        setMaxWidth(320);
        setMaxHeight(370);
        getStyleClass().addAll("criar-manutencao", "soft-drop-shadow");
    }

    private void render() {
        HBox botoesContainer = new HBox();
        Label concluirLabel = new Label("Concluir");
        Label cancelarLabel = new Label("Cancelar");
        Pane spacer = new Pane();
        Pane spacer1 = new Pane();

        titulo.getStyleClass().add("title-text");

        nome.setPrefWidth(300);
        nome.setPromptText("Nome");

        descricao.setPrefWidth(300);
        descricao.setPromptText("Descrição");

        concluirLabel.getStyleClass().add("sub-text");
        concluirLabel.setStyle("-fx-text-fill: #ffffff");
        concluirLabel.setPrefHeight(10);
        
        cancelarLabel.getStyleClass().add("sub-text");
        cancelarLabel.setStyle("-fx-text-fill: #ffffff");
        cancelarLabel.setPrefHeight(10);

        botaoConcluir.setPrefSize(100, 30);
        botaoConcluir.getStyleClass().addAll("create-window-botao", "soft-drop-shadow", "itemline-hover");
        botaoConcluir.setStyle("-fx-background-color: #2C6E49;");
        botaoConcluir.getChildren().addAll(concluirLabel);
        handleBotaoConcluir();

        botaoCancelar.setPrefSize(100, 30);
        botaoCancelar.getStyleClass().addAll("create-window-botao", "soft-drop-shadow", "itemline-hover");
        botaoCancelar.setStyle("-fx-background-color: #B80C09");
        botaoCancelar.getChildren().addAll(cancelarLabel);
        handleBotaoCancelar();

        mensagemLabel.getStyleClass().add("sub-text");
        mensagemLabel.setStyle("-fx-text-fill: #B80C09");
        mensagemLabel.setPrefHeight(30);
        mensagemLabel.setAlignment(Pos.BOTTOM_LEFT);

        botoesContainer.setSpacing(30);
        botoesContainer.setAlignment(Pos.BOTTOM_RIGHT);
        botoesContainer.setPrefSize(300, 40);
        botoesContainer.getChildren().addAll(botaoConcluir, botaoCancelar);

        spacer.setPrefHeight(10);
        spacer1.setPrefHeight(90);

        getChildren().addAll(titulo, spacer, nome, descricao, mensagemLabel, spacer1, botoesContainer);
    }

    private void handleBotaoConcluir() {
        botaoConcluir.setOnMouseClicked(e -> {
            if(validador()) {
                String nome = this.nome.getText();
                String descricao = this.descricao.getText();

                Equipamento equipamento = new Equipamento(nome, descricao);

                try {
                    EquipamentoDAO dao = new EquipamentoDAO();
                    dao.create(equipamento);
                    popup.hide();
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    mensagemLabel.setText("Erro ao Criar Equipamento");
                }
            }
        });
    }

    private void handleBotaoCancelar() {
        botaoCancelar.setOnMouseClicked(e -> {
            if(popup.isShowing()) {
                popup.hide();
            }
        });
    }

    private boolean validador() {
        if(nome.getText().trim().isEmpty()) {
            mensagemLabel.setText("Campo Nome está vazio.");
            return false;
        }
        else if(descricao.getText().trim().isEmpty()) {
            mensagemLabel.setText("Campo Descrição está vazio.");
            return false;
        }
        return true;
    }
}
