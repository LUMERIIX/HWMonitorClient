package org.example;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class Interfaces
{
    public Tile MainInterfaceTile;

    public Interfaces(double TileWidth, double TileHeight)
    {
        createMainTile(TileWidth,TileHeight);
    }

    private EventHandler<MouseEvent> MainViewInterfaceHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node source = (Node)e.getSource();
            System.out.println("Interface Main handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
        }
    };

    private void createMainTile(double TileWidth, double TileHeight)    //shows only nr of available network interfaces
    {
        MainInterfaceTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(TileWidth, TileHeight)
                .title("Interface")
                .unit("pieces")
                .animated(true)
                .textVisible(false)
                .averagingPeriod(25)
                .autoReferenceValue(true)
                .barColor(Tile.YELLOW_ORANGE)
                .barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
                .sections(new eu.hansolo.tilesfx.Section(0, 33, Tile.LIGHT_GREEN),
                        new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                        new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
                .sectionsVisible(true)
                .highlightSections(true)
                .strokeWithGradient(true)
                .gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
                        new Stop(0.33, Tile.LIGHT_GREEN),
                        new Stop(0.33,Tile.YELLOW),
                        new Stop(0.67, Tile.YELLOW),
                        new Stop(0.67, Tile.LIGHT_RED),
                        new Stop(1.0, Tile.LIGHT_RED))
                .build();

        MainInterfaceTile.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewInterfaceHandler);
    }
}
