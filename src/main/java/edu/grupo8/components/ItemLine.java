package edu.grupo8.components;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.kordamp.ikonli.javafx.FontIcon;

import edu.grupo8.ManutencoesController;
import edu.grupo8.models.Equipamento;
import edu.grupo8.models.Manutencao;
import edu.grupo8.models.Manutencao.Status;
import edu.grupo8.utils.EquipamentoDAO;
import edu.grupo8.utils.ManutencaoDAO;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.util.Duration;

public class ItemLine extends HBox {
    private CheckBox cb = new CheckBox();
    private Label lbitemName = new Label();
    private Label lbDate = new Label();
    private TextField tfItemName = new TextField();
    private TextField dayTf = new TextField();
    private TextField monthTf = new TextField();
    private TextField yearTf = new TextField();
    private HBox dataContainer = new HBox();
    private HBox statusContainer = new HBox();
    private Popup statusPopup = new Popup();
    private HBox container = new HBox();
    private HBox status1 = new HBox();
    private HBox status2 = new HBox();
    private Status status1_s;
    private Status status2_s;
    private StackPane botaoEditar = createBotaoEditar();
    private Label descricaoLb = new Label();
    private TextField descricaoTf = new TextField();
    private HBox descricaoContainer = new HBox();

    private Manutencao manutencao;
    private Equipamento equipamento;
    private ManutencoesController controller;

    private double statusContainerWidth = 70;
    private double statusContainerHeight = 25;
    private boolean editMode = false;
    
    public ItemLine(Manutencao manutencao) {
        this.manutencao = manutencao;

        String dataFormatada = this.manutencao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        setConfig();
        renderItemLineManutencao(this.manutencao.getNome(), dataFormatada);
    }

    public ItemLine(Equipamento equipamento) {
        this.equipamento = equipamento;

        setConfig();
        renderItemLineEquipamento(this.equipamento.getNome(), this.equipamento.getDescricao());
    }

    private void setConfig() {
        setMaxSize(800, 45);
        setPrefHeight(45);
        setMinHeight(45);
        getStyleClass().add("itemline");
    }

    private void renderItemLineManutencao(String itemName, String date) {
        Label title = new Label("Equipamentos");
        Label equipamentos = new Label();

        try {
            ManutencaoDAO dao = new ManutencaoDAO();
            List<Equipamento> equips = dao.listarEquipamentos(manutencao);
            String desc = "";
            for(Equipamento eq : equips) {
                desc += eq.getNome()+"; ";
            }
            equipamentos.setText(desc);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        StackPane botao = createMoreButton(equipamentos);

        container.setAlignment(Pos.CENTER);
        container.setSpacing(5);

        title.getStyleClass().add("title-text");

        container.getChildren().addAll(botao, title);

        cb.setText("");
        cb.getStyleClass().add("itemline-cbox");

        lbitemName.setPrefWidth(300);
        lbitemName.setText(itemName);
        lbitemName.getStyleClass().add("title-text");

        statusContainer.setPrefSize(statusContainerWidth, statusContainerHeight);
        statusContainer.setAlignment(Pos.CENTER);
        statusContainer.getChildren().add(StatusComponent.get(manutencao.getStatus(), statusContainerWidth, statusContainerHeight));
        statusContainer.getStyleClass().addAll("soft-drop-shadow", "itemline-hover");
        handleStatusContainerEvents();

        lbDate.setPrefWidth(100);
        lbDate.setAlignment(Pos.CENTER);
        lbDate.setText(date);
        lbDate.getStyleClass().add("title-text");

        getChildren().addAll(cb, lbitemName, container, statusContainer, lbDate, botaoEditar);
    }

    private void renderItemLineEquipamento(String itemName, String desc) {
        Label descTitle = new Label("Descrição");

        descTitle.getStyleClass().add("title-text");
        descTitle.setPrefWidth(335);

        descricaoContainer.setSpacing(10);
        descricaoContainer.setAlignment(Pos.CENTER);

        cb.setText("");
        cb.getStyleClass().add("itemline-cbox");

        lbitemName.setPrefWidth(300);
        lbitemName.setText(itemName);
        lbitemName.getStyleClass().add("title-text");

        descricaoLb.setText(desc);
        descricaoLb.getStyleClass().add("title-text");

        StackPane botaoMore = createMoreButton(descricaoLb);

        descricaoContainer.getChildren().clear();
        descricaoContainer.getChildren().addAll(botaoMore, descTitle);

        getChildren().addAll(cb, lbitemName, descricaoContainer, botaoEditar);
    }

    private StackPane createMoreButton(Label content) {
        StackPane botao = new StackPane();
        FontIcon icon = new FontIcon("mdi2p-plus-box");
        Popup popup =  new Popup();
        StackPane popupContent = new StackPane();
        Label desc = new Label(content.getText());
        
        icon.setIconSize(20);
        icon.setIconColor(Color.web("#473F78"));

        desc.setMaxWidth(200);
        desc.setWrapText(true);

        popupContent.setPadding(new Insets(15));
        popupContent.getStyleClass().addAll("itemline-desc-popup", "soft-drop-shadow");
        //popupContent.getChildren().clear();
        popupContent.getChildren().addAll(desc);

        botao.setOnMouseEntered(e -> {
            if(!popup.isShowing()) {
                double x = botao.localToScreen(botao.getBoundsInLocal()).getMinX();
                double y = botao.localToScreen(botao.getBoundsInLocal()).getMinY() + 50;

                popup.getContent().setAll(popupContent);
                popup.show(botao, x, y);

                System.out.println(popupContent.getChildren());
            }
        });

        botao.setOnMouseExited(e -> {
            if(popup.isShowing()) {
                popup.hide();
            }
        });

        botao.getChildren().addAll(icon);
        
        return botao;
    }

    private StackPane createBotaoEditar() {
        StackPane botaoAceitar = createBotaoAceitar();
        StackPane botaoCancelar = createBotaoCancelar();

        StackPane botao = new StackPane();
        Popup popup = new Popup();
        VBox popupContent = new VBox();

        Circle circle = new Circle(17);
        circle.getStyleClass().add("itemline-edit-button");

        FontIcon icon = new FontIcon("mdi2c-circle-edit-outline");
        icon.setIconSize(28);
        icon.setIconColor(Color.WHITE);

        botao.getStyleClass().addAll("itemline-hover");
        botao.getChildren().addAll(circle, icon);

        botao.setOnMouseClicked(e -> {
            if(!editMode) {
                if(manutencao != null) {
                    Label label1 = new Label("/");
                    Label label2 = new Label("/");

                    label1.getStyleClass().add("title-text");
                    label2.getStyleClass().add("title-text");

                    dayTf.getStyleClass().add("title-text");
                    dayTf.setPrefWidth(24);
                    dayTf.setText(manutencao.getData().format(DateTimeFormatter.ofPattern("dd")));

                    monthTf.getStyleClass().add("title-text");
                    monthTf.setPrefWidth(24);
                    monthTf.setText(manutencao.getData().format(DateTimeFormatter.ofPattern("MM")));

                    yearTf.getStyleClass().add("title-text");
                    yearTf.setPrefWidth(48);
                    yearTf.setText(manutencao.getData().format(DateTimeFormatter.ofPattern("yyyy")));
                    
                    dataContainer.setPrefWidth(100);
                    dataContainer.setMaxWidth(100);
                    dataContainer.setAlignment(Pos.CENTER);
                    dataContainer.getChildren().clear();
                    dataContainer.getChildren().addAll(dayTf, label1, monthTf, label2, yearTf);

                    tfItemName.setPrefWidth(300);
                    tfItemName.getStyleClass().addAll("title-text");
                    tfItemName.setText(manutencao.getNome());

                    statusContainer.setOpacity(.5);
                    statusContainer.getStyleClass().remove("itemline-hover");

                    editMode = true;

                    getChildren().clear();
                    getChildren().addAll(cb, tfItemName, container, statusContainer, dataContainer, botaoEditar);
                }
                
                if(equipamento != null) {
                    tfItemName.setPrefWidth(300);
                    tfItemName.getStyleClass().addAll("title-text");
                    tfItemName.setText(equipamento.getNome());

                    descricaoTf.setPrefWidth(335);
                    descricaoTf.getStyleClass().add("title-text");
                    descricaoTf.setText(equipamento.getDescricao());

                    descricaoContainer.getChildren().remove(1);
                    descricaoContainer.getChildren().add(descricaoTf);

                    editMode = true;

                    getChildren().clear();
                    getChildren().addAll(cb, tfItemName, descricaoContainer, botaoEditar);
                }

                // Popup
                popupContent.setSpacing(50);
                popupContent.setAlignment(Pos.CENTER);

                botaoAceitar.getStyleClass().add("itemline-hover");
                botaoCancelar.getStyleClass().add("itemline-hover");

                popupContent.getChildren().clear();
                popupContent.getChildren().addAll(botaoAceitar, botaoCancelar);

                botao.setOnMouseEntered(ev -> {
                        if(!popup.isShowing() && editMode) {
                            double x = botao.localToScreen(botao.getBoundsInLocal()).getMinX();
                            double y = botao.localToScreen(botao.getBoundsInLocal()).getMinY() - 38;

                            popup.getContent().clear();
                            popup.getContent().setAll(popupContent);
                            popup.show(botao, x, y);
                        }
                    });

                botao.setOnMouseExited(ev -> {
                    if(popup.isShowing() && editMode) {
                        PauseTransition pt = new PauseTransition(Duration.millis(150));
                        pt.setOnFinished(eve -> {
                            if(!botao.isHover() && !popupContent.isHover()) {
                                popup.hide();
                            }
                        });
                        pt.play();
                    }
                });

                popupContent.setOnMouseExited(ev -> {
                    PauseTransition pt = new PauseTransition(Duration.millis(200));
                    pt.setOnFinished(eve -> {
                        if (!botao.isHover() && !popupContent.isHover()) {
                            popup.hide();
                        }
                    });
                    pt.play();
                });
            }
        });

        return botao;
    }

    private StackPane createBotaoAceitar() {
        StackPane botao = new StackPane();

        Circle circle = new Circle(17);
        circle.getStyleClass().add("itemline-accept-button");

        FontIcon icon = new FontIcon("mdi2c-check-bold");
        icon.setIconSize(28);
        icon.setIconColor(Color.WHITE);

        botao.getStyleClass().addAll("itemline-hover");
        botao.getChildren().addAll(circle, icon);

        botao.setOnMouseClicked(e -> {
            if(editMode) {
                editMode = false;

                if(manutencao != null){
                    lbitemName.setText(tfItemName.getText());
                    lbDate.setText(dayTf.getText()+"/"+monthTf.getText()+"/"+yearTf.getText());

                    LocalDate data = LocalDate.of(Integer.parseInt(yearTf.getText()), Integer.parseInt(monthTf.getText()), Integer.parseInt(dayTf.getText()));

                    manutencao.setNome(tfItemName.getText());
                    manutencao.setData(data);

                    try {
                        ManutencaoDAO dao = new ManutencaoDAO();
                        dao.update(manutencao);
                    }
                    catch(SQLException ex) {
                        ex.printStackTrace();
                    }

                    statusContainer.setOpacity(1);
                    statusContainer.getStyleClass().add("itemline-hover");

                    getChildren().clear();
                    getChildren().addAll(cb, lbitemName, container, statusContainer, lbDate, botaoEditar);
                }

                if(equipamento != null) {
                    lbitemName.setText(tfItemName.getText());
                    descricaoLb.setText(descricaoTf.getText());

                    equipamento.setNome(tfItemName.getText());
                    equipamento.setDescricao(descricaoTf.getText());

                    try {
                        EquipamentoDAO eqDao = new EquipamentoDAO();
                        eqDao.update(equipamento);
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    getChildren().clear();
                    renderItemLineEquipamento(this.equipamento.getNome(), this.equipamento.getDescricao());
                }
            }
        });

        return botao;
    }

    private StackPane createBotaoCancelar() {
        StackPane botao = new StackPane();

        Circle circle = new Circle(17);
        circle.getStyleClass().add("itemline-cancel-button");

        FontIcon icon = new FontIcon("mdi2c-close");
        icon.setIconSize(28);
        icon.setIconColor(Color.WHITE);

        botao.getStyleClass().addAll("itemline-hover");
        botao.getChildren().addAll(circle, icon);

        botao.setOnMouseClicked(e ->{
            if(editMode) {
                editMode = false;
                statusContainer.setOpacity(1);
                statusContainer.getStyleClass().add("itemline-hover");

                getChildren().clear();

                if(manutencao != null) {
                    getChildren().addAll(cb, lbitemName, container, statusContainer, lbDate, botaoEditar);
                }

                if(equipamento != null) {
                    renderItemLineEquipamento(this.equipamento.getNome(), this.equipamento.getDescricao());
                }
            }
        });

        return botao;
    }

    private void handleStatusContainerEvents() {
        statusContainer.setOnMouseClicked(e -> {
            manageStatusPopup();
        });
    }

    private void manageStatusPopup() {
        VBox popupContent = new VBox();

        if(manutencao.getStatus() == Status.CONCLUIDO) {
            status1 = StatusComponent.get(Status.CANCELADO, statusContainerWidth, statusContainerHeight);
            status1_s = Status.CANCELADO;
            status2 = StatusComponent.get(Status.PENDENTE, statusContainerWidth, statusContainerHeight);
            status2_s = Status.PENDENTE;
        }
        else if(manutencao.getStatus() == Status.CANCELADO) {
            status1 = StatusComponent.get(Status.CONCLUIDO, statusContainerWidth, statusContainerHeight);
            status1_s = Status.CONCLUIDO;
            status2 = StatusComponent.get(Status.PENDENTE, statusContainerWidth, statusContainerHeight);
            status2_s = Status.PENDENTE;
        }
        else if(manutencao.getStatus() == Status.PENDENTE || manutencao.getStatus() == Status.ATRASADO) {
            status1 = StatusComponent.get(Status.CONCLUIDO, statusContainerWidth, statusContainerHeight);
            status1_s = Status.CONCLUIDO;
            status2 = StatusComponent.get(Status.CANCELADO, statusContainerWidth, statusContainerHeight);
            status2_s = Status.CANCELADO;
        }

        popupContent.setAlignment(Pos.CENTER);
        popupContent.setSpacing(50);
        popupContent.getChildren().addAll(status1, status2);
        popupContent.getStyleClass().add("soft-drop-shadow");

        status1.getStyleClass().addAll("itemline-hover");
        status2.getStyleClass().addAll("itemline-hover");

        status1.setOnMouseClicked(e -> {
            this.manutencao.setStatus(status1_s);
            statusContainer.getChildren().set(0, status1);
            statusPopup.hide();

            try{
                ManutencaoDAO dao = new ManutencaoDAO();
                dao.update(this.manutencao);

                controller.updateLista();
            }
            catch(SQLException ex) {
                ex.printStackTrace();
            }
        });

        status2.setOnMouseClicked(e -> {
            this.manutencao.setStatus(status2_s);
            statusContainer.getChildren().set(0, status2);
            statusPopup.hide();

            try{
                ManutencaoDAO dao = new ManutencaoDAO();
                dao.update(this.manutencao);

                controller.updateLista();
            }
            catch(SQLException ex) {
                ex.printStackTrace();
            }
        });

        statusPopup.setAutoHide(true);

        if(!statusPopup.isShowing() && !editMode) {
            double x = statusContainer.localToScreen(statusContainer.getBoundsInLocal()).getMinX();
            double y = statusContainer.localToScreen(statusContainer.getBoundsInLocal()).getMinY() - 27;

            statusPopup.setOnShown(e -> {
                TranslateTransition ttStatus1 = new TranslateTransition(Duration.millis(150), status1);
                ttStatus1.setFromY(50);
                ttStatus1.setToY(0);
                ttStatus1.setInterpolator(Interpolator.EASE_IN);

                TranslateTransition ttStatus2 = new TranslateTransition(Duration.millis(150), status2);
                ttStatus2.setFromY(-50);
                ttStatus2.setToY(0);
                ttStatus2.setInterpolator(Interpolator.EASE_IN);

                FadeTransition ft = new FadeTransition(Duration.millis(200), popupContent);
                ft.setFromValue(0);
                ft.setToValue(1);

                ttStatus1.play();
                ttStatus2.play();
                ft.play();
            });

            statusPopup.getContent().setAll(popupContent);

            statusPopup.show(statusContainer, x, y);
        }
    }

    public boolean isSelected() {
        return cb.isSelected();
    }

    public Manutencao getManutencao() {
        return this.manutencao;
    }

    public Equipamento getEquipamento() {
        return this.equipamento;
    }

    public void setControllerManutencao(ManutencoesController controller) {
        this.controller = controller;
    }
}
