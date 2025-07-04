package edu.grupo8.components;

import edu.grupo8.models.Manutencao;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusComponent {
    public static HBox get(Manutencao.Status status, double width, double height) {
        HBox container =  new HBox();
        Label title = new Label();
        String titleText;
        String color;

        switch(status) {
            case CONCLUIDO:
                color = "#2C6E49";
                titleText = "Concluído";
                break;
            case PENDENTE:
                color = "#FDCA40";
                titleText = "Pendente";
                break;
             case ATRASADO:
                color = "#B80C09";
                titleText = "Atrasado";
                break;
            case CANCELADO:
                color = "#554640";
                titleText = "Cancelado";
                break;
            default:
                color="";
                titleText="";
                break;
        }

        container.setMaxSize(width, height);
        container.setPrefSize(width, height);
        container.setAlignment(Pos.CENTER);
        container.setStyle(
            "-fx-background-color: "+color+";"+
            "-fx-background-radius: 10;"
        );

        title.setText(titleText);
        title.setPrefSize(width, height);
        title.setStyle(
            "-fx-font-weight: bold;"+
            "-fx-text-fill: #ffffff"
        );
        title.setAlignment(Pos.CENTER);

        container.getChildren().add(title);

        return container;
    }
}
