package org.example;

import eu.hansolo.tilesfx.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class Dashboard
{

    public static Background background = new Background(new BackgroundFill(Tile.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY));

    public static final    double TILE_WIDTH  = 320;
    public static final    double TILE_HEIGHT = 300;

    public static final     Color Text = Color.WHITE;

    public static GridPane createDashboardGridPane ()
    {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(20);
        gridPane.setVgap(10);

        gridPane.setBackground(Dashboard.background);
        gridPane.setPrefSize(1024,600);

        return gridPane;

    }

    public static HBox createHbox(Pos pos)
    {
        HBox hbox = new HBox();
        hbox.setFillHeight(true);
        hbox.setAlignment(pos);

        return hbox;
    }

    public static Label[] createLabelArray (int NrOfLabels, Pos pos, String[] text)
    {
        Label[] label = new Label[NrOfLabels];

        for(int i = 0; i < NrOfLabels; i++)
        {
            label[i] = new Label();
            //label[i].setPrefSize(TILE_WIDTH/2,10);
            label[i].setText(text[i]);
            label[i].setTextFill(Text);
            label[i].setAlignment(pos);
        }
        return label;
    }

    public static HBox createHBox (Label ColumnOne, Label ColumnTwo)
    {
        Region middleSpacer = new Region();
        Region sideSpacerLeft = new Region();
        Region sideSpacerRight = new Region();
        sideSpacerLeft.setPrefSize(20, 20);
        sideSpacerRight.setPrefSize(20, 20);
        middleSpacer.setPrefSize(5, 5);
        HBox.setHgrow(sideSpacerLeft, Priority.NEVER);
        HBox.setHgrow(sideSpacerRight, Priority.NEVER);
        HBox.setHgrow(middleSpacer, Priority.ALWAYS);
        HBox.setHgrow(ColumnOne, Priority.NEVER);
        HBox.setHgrow(ColumnTwo, Priority.NEVER);
        HBox hbox = new HBox(5, sideSpacerLeft,ColumnOne, middleSpacer, ColumnTwo, sideSpacerRight);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setFillHeight(true);

        return hbox;
    }

    public static HBox createHBox (Label ColumnOne, Label ColumnTwo, Label ColumnThree)
    {
        Region middleSpacer = new Region();
        Region sideSpacerLeft = new Region();
        Region sideSpacerRight = new Region();
        sideSpacerLeft.setPrefSize(20, 20);
        sideSpacerRight.setPrefSize(20, 20);
        middleSpacer.setPrefSize(5, 5);
        HBox.setHgrow(sideSpacerLeft, Priority.NEVER);
        HBox.setHgrow(sideSpacerRight, Priority.NEVER);
        HBox.setHgrow(middleSpacer, Priority.ALWAYS);
        HBox.setHgrow(ColumnOne, Priority.NEVER);
        HBox.setHgrow(ColumnTwo, Priority.NEVER);
        HBox.setHgrow(ColumnThree,Priority.NEVER);
        HBox hbox = new HBox(5, sideSpacerLeft,ColumnOne, middleSpacer, ColumnTwo, ColumnThree, sideSpacerRight);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setFillHeight(true);

        return hbox;
    }

    public static VBox createComponentTable (String [] ColumnNames, String [] CompName, int [] Value)
    {
        List<HBox> hBoxList = new ArrayList<HBox>();
        VBox tabel = new VBox();
        String[] val = new String[Value.length];
        Label[] compLabels = new Label[CompName.length];
        Label[] valueLabels = new Label[CompName.length];

        Region spacer = new Region();
        spacer.setPrefSize(5,5);


        tabel.setPrefSize(TILE_WIDTH,TILE_HEIGHT);

        for(int i = 0; i < Value.length; i++)
        {
            val[i] = String.valueOf(Value[i]);
        }

        Label columnLabelLeft = new Label(ColumnNames[0]);
        Label columnLabelRight = new Label(ColumnNames[1]);

        columnLabelLeft.setAlignment(Pos.CENTER_LEFT);
        columnLabelLeft.setTextFill(Text);
        //columnLabelLeft.setPrefSize(TILE_WIDTH,10);
        columnLabelRight.setAlignment(Pos.CENTER_RIGHT);
        columnLabelRight.setTextFill(Text);
        //columnLabelRight.setPrefSize(TILE_WIDTH,10);

        compLabels = createLabelArray(CompName.length,Pos.CENTER_LEFT,CompName);
        valueLabels = createLabelArray(Value.length,Pos.CENTER_RIGHT,val);

        //HBox hbox = new HBox();
        hBoxList.add(createHBox(columnLabelLeft,columnLabelRight));

        for(int i = 0; i < CompName.length; i++)
        {
            HBox hbox = createHBox(compLabels[i],valueLabels[i]);
            hBoxList.add(hbox);

        }

        for(int i = 0; i < hBoxList.size(); i++)
            tabel.getChildren().add(hBoxList.get(i));

        return tabel;
    }

    public static VBox createComponentTable (String [] ColumnNames, String [] CompName, int [] Value, String unit)
    {
        List<HBox> hBoxList = new ArrayList<HBox>();
        VBox tabel = new VBox();
        String[] val = new String[Value.length];
        Label[] compLabels = new Label[Value.length];
        Label[] valueLabels = new Label[Value.length];
        Label[] unitLabels = new Label[Value.length+1];
        String[] units = new String[Value.length];

        Region spacer = new Region();
        spacer.setPrefSize(5,5);


        tabel.setPrefSize(TILE_WIDTH,TILE_HEIGHT);

        for(int i = 0; i < Value.length; i++)
        {
            val[i] = String.valueOf(Value[i]);
            units[i] = unit;
        }

        Label columnLabelLeft = new Label(ColumnNames[0]);
        Label columnLabelRight = new Label(ColumnNames[1]);


        columnLabelLeft.setAlignment(Pos.CENTER_LEFT);
        columnLabelLeft.setTextFill(Text);
        columnLabelRight.setAlignment(Pos.CENTER_RIGHT);
        columnLabelRight.setTextFill(Text);


        compLabels = createLabelArray(CompName.length,Pos.CENTER_LEFT,CompName);
        valueLabels = createLabelArray(Value.length,Pos.CENTER_RIGHT,val);
        unitLabels = createLabelArray(Value.length,Pos.CENTER_RIGHT,units);

        //HBox hbox = new HBox();
        hBoxList.add(createHBox(columnLabelLeft,columnLabelRight));

        for(int i = 0; i < CompName.length; i++)
        {
            HBox hbox = createHBox(compLabels[i],valueLabels[i],unitLabels[i]);
            hBoxList.add(hbox);
        }

        for(int i = 0; i < hBoxList.size(); i++)
            tabel.getChildren().add(hBoxList.get(i));

        return tabel;
    }
}
