package org.example;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class RAM
{
    //Data
    public String Name;
    public int UsedMemory;
    public Gauge ramGauge;
    public int AvailableMemory;
    public Tile MainRamUsage;

    public RAM()
    {
        Name = new String();
        createMainTile();
    }

    private void createMainTile()
    {
        ramGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.SIMPLE_SECTION)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .sections(new Section(0, 33, Color.LIME),
                        new Section(33, 66, Color.YELLOW),
                        new Section(66, 100, Color.CRIMSON))
                .animated(true)
                .unit("%")
                .valueColor(Tile.FOREGROUND)
                .titleColor(Tile.FOREGROUND)
                .unitColor(Tile.FOREGROUND)
                .majorTickMarkColor(Tile.FOREGROUND)
                .minorTickMarkColor(Tile.FOREGROUND)
                .mediumTickMarkColor(Tile.FOREGROUND)
                .build();

        ramGauge.setBarColor(Tile.FOREGROUND);
        MainRamUsage  = TileBuilder.create()
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .skinType(Tile.SkinType.CUSTOM)
                .title("Ram Usage")
                .titleAlignment(TextAlignment.CENTER)
                .graphic(ramGauge)
                .build();
    }
}
