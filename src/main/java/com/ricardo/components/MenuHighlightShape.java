package com.ricardo.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;  
import javafx.scene.shape.QuadCurveTo;

public class MenuHighlightShape extends Path{
    double width = 190;
    double height = 60;
    double cornerRadius = height/2+12;
    double hookDepth = 25;

    public MenuHighlightShape() {
        buildShape();
        setFill(Color.WHITE);
        setStroke(null);
    }

    private void buildShape() {
        getElements().add(new MoveTo(0, 0));
        getElements().add(new QuadCurveTo(0, hookDepth, -hookDepth, hookDepth));

        getElements().add(new LineTo(-width, hookDepth));
        getElements().add(new QuadCurveTo(-width-7, cornerRadius, -width, height));

        getElements().add(new LineTo(-hookDepth, height));
        getElements().add(new QuadCurveTo(0, height, 0, height+hookDepth));
        getElements().add(new ClosePath());
    }
}
