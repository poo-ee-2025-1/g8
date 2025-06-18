package com.ricardo.components;

import java.util.List;

import com.ricardo.components.StatusComponent.Status;
import com.ricardo.models.Manutencao;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SummaryComponent extends VBox{
    Text title = new Text("Resumo");
    HBox main = new HBox();

    List<Manutencao> manutencoes;

    public SummaryComponent(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;

        this.title.getStyleClass().add("title-text");
        this.title.setStyle(
            "-fx-fill: #31393C;"
        );
        setMargin(title, new Insets(0,0,0,10));

        this.setSpacing(5);
        this.setPrefWidth(500);
        this.setMaxWidth(500);

        getChildren().addAll(title, main);

        renderStatusSummary();
    }

    private void renderStatusSummary() {
        double width = 235;
        double height = 265;

        int concluidoCount = 0;
        int pendenteCount = 0;
        int atrasadoCount = 0;
        int canceladoCount = 0;

        VBox mainContainer = new VBox();

        HBox conContainer = new HBox();
        HBox penContainer = new HBox();
        HBox atrContainer = new HBox();
        HBox canContainer = new HBox();

        Text conText = new Text();
        Text penText = new Text();
        Text atrText = new Text();
        Text canText = new Text();

        if(!manutencoes.isEmpty()) {
            for(Manutencao manutencao : manutencoes) {
                if(manutencao.getStatus().equals("concluido")) {
                    concluidoCount += 1;
                }

                if(manutencao.getStatus().equals("pendente")) {
                    pendenteCount += 1;
                }

                if(manutencao.getStatus().equals("atrasado")) {
                    atrasadoCount += 1;
                }

                if(manutencao.getStatus().equals("cancelado")) {
                    canceladoCount += 1;
                }
            }

            conText.setText(""+concluidoCount);
            penText.setText(""+pendenteCount);
            atrText.setText(""+atrasadoCount);
            canText.setText(""+canceladoCount);

            conText.getStyleClass().add("title-text");
            penText.getStyleClass().add("title-text");
            atrText.getStyleClass().add("title-text");
            canText.getStyleClass().add("title-text");

            conContainer.setPrefHeight(height/4);
            penContainer.setPrefHeight(height/4);
            atrContainer.setPrefHeight(height/4);
            canContainer.setPrefHeight(height/4);

            conContainer.setAlignment(Pos.CENTER);
            penContainer.setAlignment(Pos.CENTER);
            atrContainer.setAlignment(Pos.CENTER);
            canContainer.setAlignment(Pos.CENTER);

            conContainer.setSpacing(100);
            penContainer.setSpacing(100);
            atrContainer.setSpacing(100);
            canContainer.setSpacing(100);

            conContainer.getChildren().addAll(StatusComponent.get(Status.CONCLUIDO, 100, 20), conText);
            penContainer.getChildren().addAll(StatusComponent.get(Status.PENDENTE, 100, 20), penText);
            atrContainer.getChildren().addAll(StatusComponent.get(Status.ATRASADO, 100, 20), atrText);
            canContainer.getChildren().addAll(StatusComponent.get(Status.CANCELADO, 100, 20), canText);
        }

        mainContainer.setStyle(
            "-fx-background-radius: 15;"+
            "-fx-padding: 10;"+
            "-fx-spacing: 5;"+
            "-fx-background-color: #FFFFFF;"
        );
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPrefSize(width, height);
        mainContainer.getChildren().addAll(conContainer, penContainer, atrContainer, canContainer);

        main.getChildren().add(0, mainContainer);
    }
}
