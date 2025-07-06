package edu.grupo8.components;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class CreateBotao extends StackPane{
    private FontIcon icon;

    public CreateBotao(String iconCode, String color) {
        icon = new FontIcon(iconCode);
        icon.setIconSize(30);
        icon.setIconColor(Color.WHITE);
        
        getStyleClass().addAll("create-botao", "itemline-hover", "soft-drop-shadow");

        setStyle("-fx-background-color: "+color+";");

        setMinSize(40, 40);
        setMaxSize(60, 60);

        getChildren().add(icon);
    }
}
