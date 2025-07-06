package edu.grupo8;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.grupo8.components.CalendarComponent;
import edu.grupo8.components.StatisticComponent;
import edu.grupo8.components.SummaryComponent;
import edu.grupo8.models.Manutencao;
import edu.grupo8.models.ManutencaoAgenda;
import edu.grupo8.utils.ManutencaoDAO;
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

    List<ManutencaoAgenda> manutencoesAg = new ArrayList<>();
    List<Manutencao> manutencoes = new ArrayList<>();

    CalendarComponent calendarComponent;
    SummaryComponent summaryComponent;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        popularListas();

        calendarComponent = new CalendarComponent(LocalDate.now(), manutencoesAg);
        calendarComponent.setManutencoes(manutencoesAg);

        summaryComponent = new SummaryComponent(manutencoes);

        Platform.runLater(() -> {
            vbox.setPrefWidth(main_hbox.getWidth()/2);
        });
        spacerVbox.setPrefHeight(100);
        vbox.getChildren().addAll(statisticComponent1, spacerVbox, statisticComponent2);

        HBox.setHgrow(right_vbox, Priority.ALWAYS);
        right_vbox.setAlignment(Pos.TOP_CENTER);
        right_vbox.setSpacing(50);
        
        right_vbox.getChildren().addAll(calendarComponent, summaryComponent);
    }

    private void popularListas() {
        try{
            ManutencaoDAO dao = new ManutencaoDAO();
            for(Manutencao man : dao.readAll()) {
                ManutencaoAgenda agenda = new ManutencaoAgenda(man.getNome(), man.getData());
                manutencoesAg.add(agenda);
                manutencoes.add(man);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
