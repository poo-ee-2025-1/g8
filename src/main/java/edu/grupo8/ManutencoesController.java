package edu.grupo8;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import edu.grupo8.components.CreateBotao;
import edu.grupo8.components.CreateManutencaoWindow;
import edu.grupo8.components.ItemLine;
import edu.grupo8.models.Manutencao;
import edu.grupo8.models.Manutencao.Status;
import edu.grupo8.utils.ManutencaoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

public class ManutencoesController implements Initializable{
    @FXML
    StackPane listStackPane;

    @FXML
    HBox mainHbox;

    private CreateBotao createBotao = new CreateBotao("mdi2p-plus", "#FDCA40");
    private CreateBotao updateBotao = new CreateBotao("mdi2r-refresh", "#9B94C7");
    private CreateBotao deleteBotao = new CreateBotao("mdi2d-delete", "#B80C09");
    private Popup popup = new Popup();
    private CreateManutencaoWindow popupContent = new CreateManutencaoWindow(popup);
    private ListView<ItemLine> lista = new ListView<ItemLine>();
    private ItemLine itemline;

    private List<Manutencao> manutencoes;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateLista();

        popupContent.setController(this);

        lista.getStyleClass().add("lista-crud");
        listStackPane.getChildren().add(lista);
        lista.setSelectionModel(null);

        lista.setCellFactory(lv -> new ListCell<ItemLine>() {
            @Override
            protected void updateItem(ItemLine item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    StackPane wrapper = new StackPane(item);
                    wrapper.setAlignment(Pos.CENTER);
                    wrapper.setPrefHeight(80);
                    wrapper.setStyle("-fx-padding: 5;");
                    setGraphic(wrapper);
                }
            }
        });

        createBotao.setOnMouseClicked(e -> {
            if(!popup.isShowing()) {
                double x = listStackPane.localToScreen(listStackPane.getBoundsInLocal()).getMinX() + 260;
                double y = listStackPane.localToScreen(listStackPane.getBoundsInLocal()).getMinY() - 80;

                popup.getContent().clear();
                popup.getContent().setAll(popupContent);
                popup.show(createBotao, x, y);
            }
        });

        updateBotao.setOnMouseClicked(e -> {
            updateLista();
        });

        deleteBotao.setOnMouseClicked(e -> {
            for(ItemLine item : lista.getItems()) {
                if(item.isSelected()) {
                    try{
                        ManutencaoDAO dao = new ManutencaoDAO();
                        dao.delete(item.getManutencao());
                    } catch(SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            updateLista();
        });

        mainHbox.setSpacing(40);
        mainHbox.setPadding(new Insets(5));
        mainHbox.setAlignment(Pos.CENTER);
        
        mainHbox.getChildren().addAll(createBotao, updateBotao, deleteBotao);
    }

    public void updateLista() {
        try{
            ManutencaoDAO dao = new ManutencaoDAO();
            manutencoes = dao.readAll();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        if(!lista.equals(null)) {
            lista.getItems().clear();
            for(Manutencao man : manutencoes) {
                if(man.getStatus() == Status.PENDENTE && man.getData().isBefore(LocalDate.now())) {
                    man.setStatus(Status.ATRASADO);
                    try {
                        ManutencaoDAO dao = new ManutencaoDAO();
                        dao.update(man);
                    } catch(SQLException e) {
                        e.printStackTrace();
                    }
                }

                itemline = new ItemLine(man);
                itemline.setControllerManutencao(this);
                lista.getItems().add(itemline);
            }
        }
    }
}
