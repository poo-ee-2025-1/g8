package edu.grupo8.components;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import edu.grupo8.ManutencoesController;
import edu.grupo8.models.Equipamento;
import edu.grupo8.models.Manutencao;
import edu.grupo8.models.Manutencao.Status;
import edu.grupo8.utils.EquipamentoDAO;
import edu.grupo8.utils.ManutencaoDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class CreateManutencaoWindow extends HBox{
    private VBox main = new VBox();
    private VBox second = new VBox();
    private TextField nome =  new TextField();
    private TextField dia = new TextField();
    private TextField mes = new TextField();
    private TextField ano = new TextField();
    private HBox dataContainer = new HBox();
    private HBox concluido = StatusComponent.get(Status.CONCLUIDO, 70, 25);
    private HBox pendente = StatusComponent.get(Status.PENDENTE, 70, 25);
    private HBox cancelado = StatusComponent.get(Status.CANCELADO, 70, 25);
    private HBox atrasado = StatusComponent.get(Status.ATRASADO, 70, 25);
    private HBox statusContainer = new HBox();
    private ListView<Equipamento> equipamentos = new ListView<Equipamento>();
    private StackPane botaoConcluir = new StackPane();
    private StackPane botaoCancelar = new StackPane();
    private Label titulo = new Label("Registrar Manutenção");
    private Label mensagemLabel = new Label();

    Popup popup = new Popup();

    private HBox statusSelected;
    private String mensagem = "";
    private List<Equipamento> equipamentoList;

    private ManutencoesController controller;

    public CreateManutencaoWindow(Popup popup) {
        this.popup = popup;

        config();
        render();
    }

    private void config() {
        setSpacing(20);
        setPadding(new Insets(10));
        setMaxWidth(600);
        setMaxHeight(430);
        getStyleClass().addAll("criar-manutencao", "soft-drop-shadow");
    }

    private void render() {
        Label label1 = new Label("/");
        Label label2 = new Label("/");
        Label statusLabel = new Label("Selecione o Status");
        Label equipamentosLabel = new Label("Selecione o(s) Equipamento(s)");
        Label concluirLabel = new Label("Concluir");
        Label cancelarLabel = new Label("Cancelar");
        
        VBox container = new VBox();
        VBox secondContainer = new VBox();
        HBox botoesContainer = new HBox();
        Pane spacer = new Pane();

        nome.setPrefWidth(300);
        nome.setPromptText("Nome");

        dia.setPrefWidth(18);
        dia.setPromptText("DD");
        dia.setAlignment(Pos.CENTER);

        mes.setPrefWidth(18);
        mes.setPromptText("MM");
        mes.setAlignment(Pos.CENTER);

        ano.setPrefWidth(36);
        ano.setPromptText("AAAA");
        ano.setAlignment(Pos.CENTER);

        concluido.getStyleClass().add("itemline-hover");
        pendente.getStyleClass().add("itemline-hover");
        cancelado.getStyleClass().add("itemline-hover");
        atrasado.getStyleClass().add("itemline-hover");

        titulo.getStyleClass().add("title-text");

        equipamentos.setPrefSize(300, 370);
        equipamentos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        handleEquipamentos();

        statusLabel.getStyleClass().add("sub-text");
        
        equipamentosLabel.getStyleClass().add("sub-text");

        concluirLabel.getStyleClass().add("sub-text");
        concluirLabel.setStyle("-fx-text-fill: #ffffff");
        concluirLabel.setPrefHeight(10);
        
        cancelarLabel.getStyleClass().add("sub-text");
        cancelarLabel.setStyle("-fx-text-fill: #ffffff");
        cancelarLabel.setPrefHeight(10);

        mensagemLabel.getStyleClass().add("sub-text");
        mensagemLabel.setStyle("-fx-text-fill: #B80C09");
        mensagemLabel.setPrefHeight(30);
        mensagemLabel.setAlignment(Pos.BOTTOM_LEFT);
        mensagemLabel.setText(mensagem);

        spacer.setPrefHeight(10);

        dataContainer.setAlignment(Pos.CENTER_LEFT);
        dataContainer.setSpacing(2);
        dataContainer.getChildren().addAll(dia, label1, mes,  label2, ano);
        handleData();

        statusContainer.setAlignment(Pos.CENTER);
        statusContainer.setSpacing(10);
        statusContainer.getStyleClass().add("soft-drop-shadow");
        statusContainer.getChildren().addAll(concluido, pendente, cancelado, atrasado);
        handleStatus();

        container.setSpacing(5);
        container.getChildren().addAll(statusLabel, statusContainer);

        secondContainer.setSpacing(5);
        secondContainer.getChildren().addAll(equipamentosLabel, equipamentos);

        botaoConcluir.setPrefSize(100, 10);
        botaoConcluir.getStyleClass().addAll("create-window-botao", "soft-drop-shadow", "itemline-hover");
        botaoConcluir.setStyle("-fx-background-color: #2C6E49;");
        botaoConcluir.getChildren().addAll(concluirLabel);
        handleBotaoConcluir();

        botaoCancelar.setPrefSize(100, 10);
        botaoCancelar.getStyleClass().addAll("create-window-botao", "soft-drop-shadow", "itemline-hover");
        botaoCancelar.setStyle("-fx-background-color: #B80C09");
        botaoCancelar.getChildren().addAll(cancelarLabel);
        handleBotaoCancelar();

        botoesContainer.setSpacing(30);
        botoesContainer.setAlignment(Pos.BOTTOM_RIGHT);
        botoesContainer.setPrefSize(300, 140);
        botoesContainer.getChildren().addAll(botaoConcluir, botaoCancelar);

        main.setSpacing(30);
        main.setAlignment(Pos.TOP_LEFT);
        main.getChildren().addAll(titulo, spacer, nome, dataContainer, container, mensagemLabel);

        second.setSpacing(60);
        second.setAlignment(Pos.TOP_LEFT);
        second.getChildren().addAll(secondContainer, botoesContainer);

        getChildren().addAll(main, second);
    }

    private void handleBotaoConcluir() {
        botaoConcluir.setOnMouseClicked(e -> {
            if(validador()) {
                String nome = this.nome.getText();

                Status status = Status.PENDENTE;
                if(statusSelected.equals(this.concluido)) {
                    status = Status.CONCLUIDO;
                }
                else if(statusSelected.equals(this.pendente)) {
                    status = Status.PENDENTE;
                } 
                else if(statusSelected.equals(this.cancelado)) {
                    status = Status.CANCELADO;
                }
                else if(statusSelected.equals(this.atrasado)) {
                    status = Status.ATRASADO;
                } 

                String dia = this.dia.getText();
                String mes = this.mes.getText();
                String ano = this.ano.getText();
                LocalDate data = LocalDate.of(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));

                Manutencao manutencao = new Manutencao(nome, status, data);

                try {
                    ManutencaoDAO dao = new ManutencaoDAO();
                    EquipamentoDAO eqDao = new EquipamentoDAO();
                    
                    dao.create(manutencao);
                    
                    ObservableList<Equipamento> selecionados = equipamentos.getSelectionModel().getSelectedItems();
                    for(Equipamento eq : selecionados) {
                        eq.setManutencao(manutencao);
                        eqDao.update(eq);
                    }
                    controller.updateLista();

                    popup.hide();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    mensagemLabel.setText("Erro ao Criar Manutenção");
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

    private TextFormatter<String> createNumericFormatter() {
        return new TextFormatter<>(change -> {
            String novoTexto = change.getControlNewText();
            return novoTexto.matches("\\d*") ? change : null;
        });
    }

    private void handleData() {
        dia.setTextFormatter(createNumericFormatter());
        mes.setTextFormatter(createNumericFormatter());
        ano.setTextFormatter(createNumericFormatter());

        dia.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.length() >= 2) {
                mes.requestFocus();
            }
        });

        mes.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.length() >= 2) {
                ano.requestFocus();
            }
        });

        ano.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.length() >= 4) {
                equipamentos.requestFocus();
            }
        });
    }

    private void handleStatus() {
        for(Node child : statusContainer.getChildren()) {
            child.setOnMouseClicked(e -> {
                for(Node n : statusContainer.getChildren()) {
                    n.setOpacity(.4);
                    n.getStyleClass().clear();
                }

                Node clicado = (Node) e.getSource();
                clicado.setOpacity(1);
                clicado.getStyleClass().add("itemline-hover");
                statusSelected = (HBox) clicado;
            });
        }
    }

    private void handleEquipamentos() {
        try {
            EquipamentoDAO dao = new EquipamentoDAO();
            equipamentoList = dao.readAll();

            this.equipamentos.getItems().clear();
            this.equipamentos.getItems().addAll(equipamentoList);

            this.equipamentos.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Equipamento item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getNome());
                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
            mensagemLabel.setText("Erro ao Carregar Lista de Equipamento");
        }

    }

    private boolean validador() {
        if(nome.getText().trim().isEmpty()) {
            mensagem = "Campo Nome está vazio.";
            mensagemLabel.setText(mensagem);
            return false;
        }
        else if(dia.getText().trim().isEmpty() || mes.getText().trim().isEmpty() || ano.getText().trim().isEmpty()) {
            mensagem = "Campo Data está vazio.";
            mensagemLabel.setText(mensagem);
            return false;
        }
        else if(equipamentos.getSelectionModel().getSelectedItems().isEmpty()) {
            mensagem = "Selecione pelo menos um Equipamento.";
            mensagemLabel.setText(mensagem);
            return false;
        }
        else if(statusSelected.equals(null)) {
            mensagem = "Selecione um Status.";
            mensagemLabel.setText(mensagem);
            return false;
        }
        return true;
    }

    public void setController(ManutencoesController controller) {
        this.controller = controller;
    }
}
