package edu.grupo8.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.kordamp.ikonli.javafx.FontIcon;

import edu.grupo8.models.ManutencaoAgenda;
import edu.grupo8.utils.DateUtils;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CalendarComponent extends VBox {
    /*
     IDEIA CENTRAL
     O CalendarComponent vai ficar responsável por mostrar de forma resumida as datas da manutenção.

     A ideia é receber uma lista de ManutencaoAgenda, que vai fornecer dados específicos da Manutencao para o CalendarComponent.
     */

    private AnchorPane controlsAnchorPane = new AnchorPane();
    private HBox daysHBox = new HBox();
    private Text weekTitle = new Text();
    private StackPane layoutBackButton = new StackPane();
    VBox summaryVBox = new VBox();

    private String[] dayTitles = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"};
    private String[] monthTitles = {
        "Janeiro",
        "Fevereiro",
        "Março",
        "Abril",
        "Maio",
        "Junho",
        "Julho",
        "Agosto",
        "Setembro",
        "Outubro",
        "Novembro",
        "Dezembro"
    };
    
    private LocalDate date;
    private LocalDate dateModified;
    private int weekCounter = 0;
    private VBox daySelected = new VBox();
    private List<ManutencaoAgenda> manutencoes;

    public CalendarComponent(LocalDate date, List<ManutencaoAgenda> manutencoes) {
        this.date = date;
        this.dateModified = date;
        this.manutencoes = manutencoes;

        this.setPrefWidth(500);
        this.setMaxWidth(500);

        renderCalendarComponent();
    }

    private VBox displayDayVBox(String dayT, String dayN, String dayY) {
        VBox display = new VBox();
        Pane spacer = new Pane();
        Text dayTitle = new Text();
        Text dayNumber = new Text();
        Circle mark = new Circle();

        display.setPrefSize(50, 80);
        display.setMaxSize(50, 80);
        display.setAlignment(Pos.TOP_CENTER);
        display.setPadding(new Insets(5));
        display.setSpacing(10);
        display.setStyle(
            "-fx-background-color: #F4F5F6;"+
            "-fx-background-radius: 10;"+
            "-fx-effect: dropshadow(gaussian, rgba(119, 97, 65, 0.2), 15, .2, 0, 5);"
        );
        
        dayTitle.setText(dayT);
        dayTitle.setStyle(
            "-fx-fill: #31393C;"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 14;"
        );

        spacer.setPrefHeight(5);

        dayNumber.setText(dayN);
        dayNumber.setStyle(
            "-fx-fill: #49565A;"
        );

        mark.setRadius(3);
        mark.setStyle(
            "-fx-fill:rgb(219, 39, 39)"
        );
        
        display.getChildren().addAll(dayTitle, spacer, dayNumber);

        display.setOnMouseEntered(e -> {
            if(!display.equals(daySelected)){
                TranslateTransition tt = new TranslateTransition(Duration.millis(50), display);
                tt.setToY(-5);
                tt.setInterpolator(Interpolator.EASE_IN);
                tt.play();
            }
        });

        display.setOnMouseExited(e -> {
            if(!display.equals(daySelected)) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(50), display);
                tt.setToY(0);
                tt.setInterpolator(Interpolator.EASE_OUT);
                tt.play();
            }
        });

        display.setOnMouseClicked(e -> {
            selectDay(display, dayN, dayY);
        });

        if(dayT.equals(""+dayTitles[date.getDayOfWeek().getValue()-1]) && dayN.equals(""+date.getDayOfMonth())) {
            display.setStyle(
                "-fx-background-color: #33A1FD;"+
                "-fx-background-radius: 10;"+
                "-fx-effect: dropshadow(gaussian, rgba(119, 97, 65, 0.2), 15, .2, 0, 5);"
            );

            dayTitle.setStyle(
                "-fx-fill:rgb(255, 255, 255);"+
                "-fx-font-weight: bold;"+
                "-fx-font-size: 14;"
            );

            dayNumber.setStyle(
                "-fx-fill:rgb(255, 255, 255);"
            );

            daySelected = display;

            selectDay(display, dayN, dayY);
        }

        for(ManutencaoAgenda manutencao : manutencoes) {
            LocalDate data = manutencao.getData();
            if(data.getDayOfMonth() == Integer.parseInt(dayN) && data.getYear() == Integer.parseInt(dayY)) {
                if(!display.getChildren().contains(mark)) {
                    display.getChildren().add(mark);
                }
            }
        }

        return display;
    }

    private void selectDay(VBox display, String dayN, String dayY) {
        DropShadow dropShadow = new DropShadow();
        List<String> nomes = new ArrayList<>();

        if(!daySelected.equals(display)) {
            TranslateTransition tt1 = new TranslateTransition(Duration.millis(50), daySelected);
            tt1.setNode(daySelected);
            tt1.setToY(0);
            tt1.setInterpolator(Interpolator.EASE_IN);
            tt1.play();

            DropShadow dropShadow1 = new DropShadow();
            dropShadow1.setBlurType(BlurType.GAUSSIAN);
            dropShadow1.setColor(Color.rgb(119, 97, 65, 0.2));
            dropShadow1.setRadius(15);
            dropShadow1.setSpread(0.2);
            dropShadow1.setOffsetX(0);
            dropShadow1.setOffsetY(5);
            daySelected.setEffect(null);
            daySelected.setEffect(dropShadow1);
        }

        TranslateTransition tt = new TranslateTransition(Duration.millis(30), display);
        tt.setToY(-10);
        tt.setInterpolator(Interpolator.EASE_IN);
        tt.play();

        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.rgb(119, 97, 65, 0.3));
        dropShadow.setRadius(15);
        dropShadow.setSpread(0.2);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(8);
        display.setEffect(null);
        display.setEffect(dropShadow);

        for(ManutencaoAgenda manutencao : manutencoes) {
            LocalDate data = manutencao.getData();
            if(data.getDayOfMonth() == Integer.parseInt(dayN) && data.getYear() == Integer.parseInt(dayY)) {
                nomes.add(manutencao.getNome());
            }
        }

        displaySummary(dayN, nomes);
        
        daySelected = display;
    }

    public void displaySummary(String dayN, List<String> nomes) {
        Text summaryTitle = new Text();
        VBox eventListVBox = new VBox();
        ScrollPane scroll = new ScrollPane();

        if(nomes.size() == 0) {
            nomes.add("Não há manutenções agendadas...");
        }

        for(String nome : nomes) {
            Label eventLabel = new Label(nome);
            eventLabel.setWrapText(true);
            eventLabel.setMaxWidth(500);
            eventListVBox.getChildren().add(eventLabel);
        }

        summaryTitle.setText("Manutenções do Dia "+dayN);
        summaryTitle.getStyleClass().add("title-text");

        scroll.setPrefSize(400, 150);
        scroll.setFitToWidth(true);
        scroll.setContent(eventListVBox);
        scroll.setStyle("-fx-background-color: transparent");

        summaryVBox.setPrefSize(500, 150);
        summaryVBox.setStyle(
            "-fx-background-color: #F4F5F6;"+
            "-fx-padding: 10;"+
            "-fx-background-radius: 15;"+
            "-fx-effect: dropshadow(gaussian, rgba(119, 97, 65, 0.1), 15, .1, 0, 5);"
        );

        summaryVBox.getChildren().clear();
        summaryVBox.getChildren().addAll(summaryTitle, scroll);
        
        this.getChildren().remove(summaryVBox);
        this.getChildren().add(summaryVBox);
    }

    private HBox arrowButton() {
        HBox main = new HBox();
        StackPane leftStackPane = new StackPane();
        StackPane rightStackPane = new StackPane();
        Region leftRegion = new Region();
        Region rightRegion = new Region();
        FontIcon left = new FontIcon("mdi2c-chevron-left");
        FontIcon right = new FontIcon("mdi2c-chevron-right");
        
        main.setPrefSize(50, 20);

        leftStackPane.setPrefSize(50, 20);
        rightStackPane.setPrefSize(50, 20);

        leftRegion.setPrefSize(50, 20);
        leftRegion.setStyle(
            "-fx-background-radius: 20 0 0 20;"+
            "-fx-background-color: #33A1FD;"
        );
        rightRegion.setPrefSize(50, 20);
        rightRegion.setStyle(
            "-fx-background-radius: 0 20 20 0;"+
            "-fx-background-color: #33A1FD;"
        );

        left.setIconSize(25);
        left.setIconColor(Color.WHITE);

        right.setIconSize(25);
        right.setIconColor(Color.WHITE);

        leftStackPane.getChildren().addAll(leftRegion, left);
        rightStackPane.getChildren().addAll(rightRegion, right);

        handleOnLeftArrowEvents(leftStackPane);
        handleOnRightArrowEvents(rightStackPane);

        main.setStyle(
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.3, 0, 1);"
        );
        main.getChildren().addAll(leftStackPane, rightStackPane);

        return main;
    }

    private List<VBox> createDayDisplaysAndTitle(LocalDate[] week) {
        List<VBox> displays = new ArrayList<>();
        String titleFirstDay = "";
        String titleLastDay = "";
        String titleMonth = "";
        
        for(int i = 0; i < 7; i++) {
            VBox display = displayDayVBox(dayTitles[i], ""+week[i].getDayOfMonth(), ""+week[i].getYear());
            
            displays.add(display);

            if (i == 0) {
                titleFirstDay = ""+week[i].getDayOfMonth();
                titleMonth = monthTitles[week[i].getMonthValue()-1];
            }

            if (i == 6) {
                titleLastDay = ""+week[i].getDayOfMonth();
            }

            if (i == date.getDayOfWeek().getValue()-1) {
                titleMonth = monthTitles[week[i].getMonthValue()-1];
            }
        }

        weekTitle.setText(titleMonth+"  "+titleFirstDay+" - "+titleLastDay);

        return displays;
    }

    private void handleOnLeftArrowEvents(StackPane leftArrow) {
        leftArrow.setOnMouseClicked(e -> {
            renderDays(-1);
            weekCounter -= 1;

            renderBackToCurrentWeekButton();
        });

        leftArrow.setOnMouseEntered(e -> {
            leftArrow.setOpacity(.7);
        });

        leftArrow.setOnMouseExited(e -> {
            leftArrow.setOpacity(1);
        });
    }

    private void handleOnRightArrowEvents(StackPane rightArrow) {
        rightArrow.setOnMouseClicked(e -> {
            renderDays(1);
            weekCounter += 1;

            renderBackToCurrentWeekButton();
        });

        rightArrow.setOnMouseEntered(e -> {
            rightArrow.setOpacity(.7);
        });
        
        rightArrow.setOnMouseExited(e -> {
            rightArrow.setOpacity(1);
        });
    }

    private void renderCalendarComponent() {
        List<VBox> displays = createDayDisplaysAndTitle(DateUtils.getSemanaAtual(date));
        HBox arrowButton = arrowButton();

        controlsAnchorPane.setMaxWidth(500);
        controlsAnchorPane.getChildren().addAll(weekTitle, arrowButton);
        AnchorPane.setLeftAnchor(weekTitle, 7.0);
        AnchorPane.setBottomAnchor(weekTitle, 0.0);
        AnchorPane.setRightAnchor(arrowButton, controlsAnchorPane.getWidth()+50);
        AnchorPane.setBottomAnchor(arrowButton, 0.0);

        weekTitle.getStyleClass().add("title-text");
        weekTitle.setStyle(
            "-fx-fill: #31393C;"
        );

        daysHBox.setSpacing(30);
        daysHBox.getChildren().addAll(displays);

        this.setSpacing(17);
        this.getChildren().add(0, controlsAnchorPane);
        this.getChildren().add(1, daysHBox);
    }

    private void renderDays(int weekQuantity) {
        dateModified = dateModified.plusWeeks(weekQuantity);
        List<VBox> displays = createDayDisplaysAndTitle(DateUtils.getSemanaAtual(dateModified));

        this.getChildren().remove(daysHBox);
        
        daysHBox.getChildren().clear();

        daysHBox.getChildren().addAll(displays);

        this.getChildren().add(1, daysHBox);

        FadeTransition ft = new FadeTransition(Duration.millis(300), daysHBox);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setInterpolator(Interpolator.EASE_BOTH);
        ft.play();

        TranslateTransition tt = new TranslateTransition(Duration.millis(300), daysHBox);
        tt.setFromY(10);
        tt.setToY(0);
        tt.setInterpolator(Interpolator.EASE_BOTH);
        tt.play();
    }

    private void renderBackToCurrentWeekButton() {
        layoutBackButton.setOnMouseClicked(e -> {
            renderDays(-weekCounter);

            weekCounter = 0;

            FadeTransition ft = new FadeTransition(Duration.millis(100), layoutBackButton);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setInterpolator(Interpolator.EASE_BOTH);
            ft.setOnFinished(event -> {controlsAnchorPane.getChildren().remove(layoutBackButton); layoutBackButton.getChildren().clear();});
            ft.play();
        });

        layoutBackButton.setOnMouseEntered(e -> {
            layoutBackButton.setOpacity(.7);
        });
        layoutBackButton.setOnMouseExited(e -> {
            layoutBackButton.setOpacity(1);
        });

        if (!controlsAnchorPane.getChildren().contains(layoutBackButton)) {
            controlsAnchorPane.getChildren().add(layoutBackButton);
            AnchorPane.setLeftAnchor(layoutBackButton, controlsAnchorPane.getWidth()-28);
            AnchorPane.setBottomAnchor(layoutBackButton, 0.0);

            Region region = new Region();
            region.setPrefSize(24, 24);
            region.setStyle(
                "-fx-background-color: #33A1FD;"+
                "-fx-background-radius: 90;"+
                "-fx-effect: dropshadow(gaussian, rgba(119, 97, 65, 0.2), 15, .2, 0, 5);"
            );

            FontIcon icon = new FontIcon("mdi2u-undo-variant");
            icon.setIconSize(20);
            icon.setIconColor(Color.WHITE);

            layoutBackButton.getChildren().addAll(region, icon);

            FadeTransition ft = new FadeTransition(Duration.millis(300), layoutBackButton);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setInterpolator(Interpolator.EASE_BOTH);
            ft.play();

            TranslateTransition tt = new TranslateTransition(Duration.millis(300), layoutBackButton);
            tt.setFromX(5);
            tt.setToX(0);
            tt.setInterpolator(Interpolator.EASE_BOTH);
            tt.play();
        }
    }
}
