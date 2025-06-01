package com.ricardo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.ricardo.components.CalendarComponent;
import com.ricardo.components.StatisticComponent;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DashboardController implements Initializable{
    @FXML
    VBox vbox;

    @FXML
    VBox right_vbox;

    @FXML
    HBox main_hbox;

    Pane spacerVbox = new Pane();

    StatisticComponent statisticComponent1 = new StatisticComponent("Manutenções", "Concluídos", "Cancelados", new Text("Work In Progress"), new Text("Work In Progress"));
    StatisticComponent statisticComponent2 = new StatisticComponent("Equipamentos", "Concluídos", "Cancelados", new Text("Work In Progress"), new Text("Work In Progress"));

    CalendarComponent calendarComponent = new CalendarComponent(LocalDate.now());

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Platform.runLater(() -> {
            vbox.setPrefWidth(main_hbox.getWidth()/2);
        });
        spacerVbox.setPrefHeight(100);
        vbox.getChildren().addAll(statisticComponent1, spacerVbox, statisticComponent2);

        HBox.setHgrow(right_vbox, Priority.ALWAYS);
        right_vbox.setAlignment(Pos.TOP_CENTER);
        right_vbox.getChildren().add(calendarComponent);
    }
}
