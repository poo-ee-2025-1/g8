package com.ricardo.components;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class StatisticComponent extends VBox {
    // Elementos da Menu Bar
    private AnchorPane mbAnchorPane = new AnchorPane();
    private HBox mbHBox = new HBox();
    private StackPane mbContentStackPane1 = new StackPane();
    private StackPane mbContentStackPane2 = new StackPane();
    private Text mbContentText1 = new Text();
    private Text mbContentText2 = new Text();
    private Text mbTitle = new Text();
    private Region mbHighlight = new Region();

    // StackPane que mostra os gráficos plotados
    private StackPane graphicStackPane = new StackPane();

    private Node graphicContent1;
    private Node graphicContent2;

    private int selectedOptionIndex = 0;

    private String MAIN_COLOR = "#FEECD8";
    private String HIGHLIGHT_COLOR = "#FAA94C";
    private String TEXT_COLOR = "#31393C";
    private double MAIN_WIDTH = 500;
    private double MAIN_HEIGHT = 350;
    private double MB_MAIN_WIDTH = 120;
    private double MB_MAIN_HEIGHT = 30;


    public StatisticComponent(String title, String text1, String text2, Node graphic1, Node graphic2) {
        this.graphicContent1 = graphic1;
        this.graphicContent2 = graphic2;

        // Configurações do Root
        this.setPrefSize(MAIN_WIDTH, MAIN_HEIGHT);
        this.setMaxSize(MAIN_WIDTH, MAIN_HEIGHT);
        this.setSpacing(5);

        // Connfigurações dos Elementos da Menu Bar
            // AnchorPane
        mbAnchorPane.setPrefWidth(MAIN_WIDTH);
        mbAnchorPane.setMaxHeight(MB_MAIN_HEIGHT);

            // HBox
        mbHBox.setPrefWidth(MAIN_WIDTH);
        mbHBox.setPrefHeight(MB_MAIN_HEIGHT);
        mbHBox.setSpacing(10);
        mbHBox.setAlignment(Pos.CENTER_RIGHT);

            // Content Text 1
        mbContentText1.setText(text1);
        mbContentText1.getStyleClass().add("mbbutton-text");
        mbContentText1.setStyle("-fx-fill: #ffffff");

            // Content Text 2
        mbContentText2.setText(text2);
        mbContentText2.getStyleClass().add("mbbutton-text");
        mbContentText2.setStyle("-fx-fill: #31393C");

            // Title
        mbTitle.setText(title);
        mbTitle.getStyleClass().add("title-text");
        mbTitle.setStyle("-fx-fill: "+TEXT_COLOR);

            // Content StackPane 1
        mbContentStackPane1.setPrefWidth(MB_MAIN_WIDTH);
        mbContentStackPane1.setPrefHeight(MB_MAIN_HEIGHT);
        StackPane.setMargin(mbContentText1, new Insets(5,5,5,5));

            // Content StackPane 2
        mbContentStackPane2.setPrefWidth(MB_MAIN_WIDTH);
        mbContentStackPane2.setPrefHeight(MB_MAIN_HEIGHT);
        StackPane.setMargin(mbContentText2, new Insets(5,5,5,5));

            // Highlight
        mbHighlight.setPrefSize(MB_MAIN_WIDTH, MB_MAIN_HEIGHT);
        mbHighlight.toBack();
        mbHighlight.setStyle("-fx-background-color: "+HIGHLIGHT_COLOR+"; -fx-background-radius: 15 15 15 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.3, 0, 1);");
        mbContentStackPane1.boundsInParentProperty().addListener((obs, oldBounds, newBounds) -> {
            mbHighlight.setLayoutX(newBounds.getMinX());
        });
        
        // Configurações do Graphic StackPane
        graphicStackPane.setPrefSize(MAIN_WIDTH, MAIN_HEIGHT);
        graphicStackPane.setStyle("-fx-background-color: "+MAIN_COLOR+"; -fx-background-radius: 25 25 25 25; -fx-effect: dropshadow(gaussian, rgba(119, 97, 65, 0.1), 15, .2, 0, 5);");

        // Organização da Hierarquia
        mbContentStackPane1.getChildren().add(mbContentText1);
        mbContentStackPane2.getChildren().add(mbContentText2);

        mbHBox.getChildren().add(0, mbContentStackPane1);
        mbHBox.getChildren().add(1, mbContentStackPane2);

        mbAnchorPane.getChildren().addAll(mbTitle, mbHighlight, mbHBox);
        AnchorPane.setBottomAnchor(mbTitle, 4.0);
        AnchorPane.setLeftAnchor(mbTitle, 10.0);

        this.getChildren().add(0, mbAnchorPane);
        this.getChildren().add(1, graphicStackPane);

        graphicStackPane.getChildren().add(graphicContent1);
        onMbButtonClicked();
        onMbButtonHovered();
    }

    private void onMbButtonClicked() {
        mbContentStackPane1.setOnMouseClicked(e -> {
            if (selectedOptionIndex == 1) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(200), mbHighlight);
                tt.setToX(0);
                tt.setInterpolator(Interpolator.EASE_BOTH);
                tt.setOnFinished(event -> mbContentText1.setStyle("-fx-fill: #ffffff"));
                tt.play();

                mbContentText2.setStyle("-fx-fill: #31393C");

                selectedOptionIndex = 0;

                graphicStackPane.getChildren().remove(0);
                graphicStackPane.getChildren().add(graphicContent1);
            }
        });

        mbContentStackPane2.setOnMouseClicked(e -> {
            if (selectedOptionIndex == 0) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(200), mbHighlight);
                tt.setToX(mbContentStackPane2.getBoundsInParent().getMinX() - mbContentStackPane1.getBoundsInParent().getMinX());
                tt.setInterpolator(Interpolator.EASE_BOTH);
                tt.setOnFinished(event -> mbContentText2.setStyle("-fx-fill: #ffffff"));
                tt.play();

                mbContentText1.setStyle("-fx-fill: #31393C");

                selectedOptionIndex = 1;
                

                graphicStackPane.getChildren().remove(0);
                graphicStackPane.getChildren().add(graphicContent2);
            }
        });
    }

    private void onMbButtonHovered() {
        mbContentStackPane1.setOnMouseEntered(e -> {
            if (selectedOptionIndex == 1) {
                mbContentText1.setStyle("-fx-fill: "+HIGHLIGHT_COLOR);
            }
        });
        mbContentStackPane1.setOnMouseExited(e -> {
            if (selectedOptionIndex == 1) {
                mbContentText1.setStyle("-fx-fill: #31393C");
            }
        });

        mbContentStackPane2.setOnMouseEntered(e -> {
            if (selectedOptionIndex == 0) {
                mbContentText2.setStyle("-fx-fill: "+HIGHLIGHT_COLOR);
            }
        });
        mbContentStackPane2.setOnMouseExited(e -> {
            if (selectedOptionIndex == 0) {
                mbContentText2.setStyle("-fx-fill: #31393C");
            }
        });
    }
}