package org.example;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import java.lang.String;

public class Storage
{
    public Tile MainStorageUsage;

    public Storage(double TileWidth, double TileHeight)
    {
        createMainTile(TileWidth,TileHeight);
    }

    private EventHandler<MouseEvent> MainViewStorageHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node source = (Node)e.getSource();
            System.out.println("Storage Main handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
        }
    };

    private Gauge createStorageGauge(String title, double TileWidth, double TileHeight)
    {
        Gauge storage = GaugeBuilder.create()
                .skinType(Gauge.SkinType.LEVEL)
                .title(title)
                .prefSize(TileWidth,TileHeight)
                .titleColor(Color.WHITE)
                .animated(true)
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.GREEN),
                        new Stop(0.25, Color.YELLOWGREEN),
                        new Stop(0.5, Color.YELLOW),
                        new Stop(0.75, Color.ORANGE),
                        new Stop(1.0, Color.RED))
                .build();
        return storage;
    }

    private void createMainTile(double TileWidth, double TileHeight)
    {
        Gauge storage = createStorageGauge("Total Disk Usage",TileWidth,TileHeight);
        MainStorageUsage  = TileBuilder.create()
                .prefSize(TileWidth, TileHeight)
                .skinType(Tile.SkinType.CUSTOM)
                .title("Total Disk Usage")
                .graphic(storage)
                .build();
    }
}
