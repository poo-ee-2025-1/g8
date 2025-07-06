package edu.grupo8;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import edu.grupo8.components.CreateBotao;
import edu.grupo8.components.CreateEquipamentoWindow;
import edu.grupo8.components.ItemLine;
import edu.grupo8.models.Equipamento;
import edu.grupo8.utils.EquipamentoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

public class EquipamentosController implements Initializable{
    @FXML
    StackPane listStackPane;

    @FXML
    HBox mainHbox;

    StackPane createBotao = new CreateBotao("mdi2p-plus", "#FDCA40");
    StackPane updateBotao = new CreateBotao("mdi2r-refresh", "#9B94C7");
    CreateBotao deleteBotao = new CreateBotao("mdi2d-delete", "#B80C09");
    Popup popup = new Popup();
    CreateEquipamentoWindow popupContent = new CreateEquipamentoWindow(popup);
    ListView<ItemLine> lista = new ListView<ItemLine>();

    private List<Equipamento> equipamentos;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateLista();

        popupContent.setControlller(this);

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
                double x = listStackPane.localToScreen(listStackPane.getBoundsInLocal()).getMinX() + 340;
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
                        EquipamentoDAO dao = new EquipamentoDAO();
                        dao.delete(item.getEquipamento());
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
        ItemLine itemline;

        try{
            EquipamentoDAO dao = new EquipamentoDAO();
            equipamentos = dao.readAll();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        if(!lista.equals(null)) {
            lista.getItems().clear();
            for(Equipamento eq : equipamentos) {
                itemline = new ItemLine(eq);
                lista.getItems().add(itemline);
            }
        }
    }
}
