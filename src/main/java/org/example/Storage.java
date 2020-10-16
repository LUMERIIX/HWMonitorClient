package org.example;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;

import java.lang.String;

public class Storage
{
    public Tile MainStorageUsage;

    public Storage()
    {
        createMainTile();
    }

    private EventHandler<MouseEvent> MainViewStorageHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            /**Node target = (Node);
            Node source = (Node)e.getSource();
            Parent parent = source.getParent();**/
            System.out.println("Storage Main handle");
            var column = GridPane.getColumnIndex(MainStorageUsage);
            var row = GridPane.getRowIndex(MainStorageUsage);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
        }
    };

    private Gauge createStorageGauge(String title)
    {
        Gauge storage = GaugeBuilder.create()
                .skinType(Gauge.SkinType.LEVEL)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .titleColor(Color.WHITE)
                .animated(true)
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.GREEN),
                        new Stop(0.25, Color.YELLOWGREEN),
                        new Stop(0.5, Color.YELLOW),
                        new Stop(0.75, Color.ORANGE),
                        new Stop(1.0, Color.RED))
                .build();

        storage.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewStorageHandler);

        return storage;
    }

    private void createMainTile() {
        Gauge storage = createStorageGauge("");
        MainStorageUsage = TileBuilder.create()
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .skinType(Tile.SkinType.CUSTOM)
                .title("Total Disk Usage")
                .titleAlignment(TextAlignment.CENTER)
                .graphic(storage)
                .build();
    }
}
