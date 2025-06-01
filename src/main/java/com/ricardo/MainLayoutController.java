package com.ricardo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.Icon;

import com.ricardo.components.MenuHighlightShape;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MainLayoutController implements Initializable{
    @FXML
    StackPane mainStackPane;

    @FXML
    AnchorPane sidebarAnchorPane;

    @FXML
    ImageView logo_image;

    MenuHighlightShape highlight = new MenuHighlightShape();

    private double highlightAnimationTime = 200;
    private Label highlightedLabel = new Label();
    private Icon highlightedIcon = null;
    private String highlightedColorHex = "#FAA94C";
    private String mainIconColor = "#9B94C7";

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        logo_image.setStyle("-fx-effect: dropshadow(gaussian, rgba(41, 31, 56, 0.56), 15, .4, 0, 2)");
    }

    private void highlightAnimation(Node buttonHBox) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(highlightAnimationTime), highlight);
        tt.setFromX(300);
        tt.setToX(0);
        tt.setInterpolator(Interpolator.EASE_BOTH);
        tt.play();

        highlight.setLayoutX(189);
        Platform.runLater(() -> {
            double hboxY = buttonHBox.getBoundsInParent().getMinY();
            double hboxHeight = buttonHBox.getBoundsInParent().getHeight();
            double highlightOffset = highlight.getBoundsInParent().getHeight() / 2;

            highlight.setLayoutY(hboxY + hboxHeight / 2 - highlightOffset);
        });

        if (!sidebarAnchorPane.getChildren().contains(highlight)) {
            sidebarAnchorPane.getChildren().add(0, highlight);
        }
    }

    private void loadScreen(String fxml) {
        new Thread(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ricardo/views/" + fxml + ".fxml"));
                Parent content = loader.load();

                Platform.runLater(() -> {
                    mainStackPane.getChildren().set(0, content);
                    StackPane.setMargin(content, new Insets(30, 30, 30, 30));
                    fadeTransition(content);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void fadeTransition(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(200), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setInterpolator(Interpolator.EASE_BOTH);
        ft.play();
    }

    public void onSidebarButtonClicked(Event e) {
        HBox sidebarButtonNode = (HBox) e.getSource();
        Label sbbLabel = (Label) sidebarButtonNode.getChildren().get(1);
        Icon sbbIcon = (Icon) sidebarButtonNode.getChildren().get(0);

        if (!sbbLabel.equals(highlightedLabel) & !sbbLabel.equals(null)) {
            highlightAnimation(sidebarButtonNode);
            Timeline time = new Timeline(
                new KeyFrame(Duration.millis(0), event -> highlightedLabel = sbbLabel),
                new KeyFrame(Duration.millis(0), event -> highlightedIcon = sbbIcon),
                new KeyFrame(Duration.millis(highlightAnimationTime), event -> sbbLabel.setStyle("-fx-text-fill: "+highlightedColorHex)),
                new KeyFrame(Duration.millis(highlightAnimationTime), event -> sbbIcon.setIconColor(Color.web(highlightedColorHex)))
            );
            time.play();

            if (highlightedIcon != null) {
                highlightedLabel.setStyle("-fx-text-fill: #ffffff");
                highlightedIcon.setIconColor(Color.web(mainIconColor));
            }

            if (sbbLabel.getText().equals("Dashboard")) {
                loadScreen("Dashboard");
            }
            else if (sbbLabel.getText().equals("Manutenções")) {
                loadScreen("Manutencoes");
            }
            else if (sbbLabel.getText().equals("Equipamentos")) {
                loadScreen("Equipamentos");
            }
            else if (sbbLabel.getText().equals("Configurações")) {
                loadScreen("Configuracoes");
            }
        }
    }

    public void onSidebarButtonMouseEntered(Event e) {
        HBox sidebarButtonNode = (HBox) e.getSource();
        Icon sbbIcon = (Icon) sidebarButtonNode.getChildren().get(0);

        sidebarButtonNode.getChildren().get(1).setStyle("-fx-text-fill: "+highlightedColorHex);
        sbbIcon.setIconColor(Color.web(highlightedColorHex));
    }

    public void onSidebarButtonMouseExited(Event e) {
        HBox sidebarButtonNode = (HBox) e.getSource();
        Icon sbbIcon = (Icon) sidebarButtonNode.getChildren().get(0);

        if (!sidebarButtonNode.getChildren().get(1).equals(highlightedLabel)) {
            sidebarButtonNode.getChildren().get(1).setStyle("-fx-text-fill: #ffffff");
            sbbIcon.setIconColor(Color.web(mainIconColor));
        }
    }

    public void onLogoImageMouseEnter() {
        RotateTransition rt = new RotateTransition(Duration.millis(300), logo_image);
        rt.setByAngle(-45);
        rt.setInterpolator(Interpolator.EASE_IN);
        rt.play();
    }

    public void onLogoImageMouseExit() {
        RotateTransition rt = new RotateTransition(Duration.millis(300), logo_image);
        rt.setFromAngle(logo_image.getRotate());
        rt.setToAngle(0);
        rt.setInterpolator(Interpolator.EASE_OUT);
        rt.play();
    }
}
