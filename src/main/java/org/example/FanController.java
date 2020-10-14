package org.example;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;

public class FanController
{
    public Tile MainFanTile;

    public FanController(double TileWidth, double TileHeight)
    {
        createMainTile(TileWidth,TileHeight);
    }

    private EventHandler<MouseEvent> MainViewFanControllerHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node source = (Node)e.getSource();
            System.out.println("Fan Main handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
        }
    };

    private void createMainTile(double TileWidth, double TileHeight)
    {
        MainFanTile = TileBuilder.create().skinType(Tile.SkinType.CHARACTER)
                .prefSize(TileWidth, TileHeight)
                .title("Fans")
                .titleAlignment(TextAlignment.CENTER)
                .description("-")
                .build();

        MainFanTile.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewFanControllerHandler);
    }
}
