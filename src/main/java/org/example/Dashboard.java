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

    static VBox createComponentTable (String [] ColumnNames, String [] CompName, int [] Value)
    {
        List<HBox> hBoxList = new ArrayList<HBox>();
        VBox tabel = new VBox();

        Label[] columnLabels = new Label[ColumnNames.length];
        Label[] compLabels = new Label[CompName.length];
        Label[] valueLabels = new Label[Value.length];

        /**if(CompName.length != Value.length)
            throw new Exception("Size don't mactch");**/

        for(int i = 0; i < ColumnNames.length; i++)
        {
            columnLabels[i] = new Label(ColumnNames[i]);
        }
        for(int i = 0; i < CompName.length; i++)
        {
            compLabels[i] = new Label(CompName[i]);
        }
        for(int i = 0; i < Value.length; i++)
        {
            valueLabels[i] = new Label(String.valueOf(Value[i]));
        }

        hBoxList.add(new HBox(5,columnLabels));

        for(int i = 0; i < CompName.length; i++)
        {
            hBoxList.add(new HBox(5,compLabels[i],valueLabels[i]));
        }

        for(int i = 0; i < hBoxList.size(); i++)
            tabel.getChildren().add(hBoxList.get(i));

        return tabel;
    }
}
