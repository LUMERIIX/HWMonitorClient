package org.example;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class NetworkInterface
{
    public Tile MainInterfaceTile;

    public NetworkInterface()
    {
        createMainTile();
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

    private void createMainTile()    //shows only nr of available network interfaces
    {
        MainInterfaceTile = TileBuilder.create().skinType(Tile.SkinType.CHARACTER)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("Interfaces")
                .titleAlignment(TextAlignment.CENTER)
                .description("-")
                .build();

        MainInterfaceTile.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewInterfaceHandler);
    }
}
