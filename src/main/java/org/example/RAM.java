package org.example;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
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
        Gauge ramGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.SIMPLE_SECTION)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .animated(true)
                .unit("%")
                .valueColor(Tile.FOREGROUND)
                .titleColor(Tile.FOREGROUND)
                .unitColor(Tile.FOREGROUND)
                .barColor(Tile.BLUE)
                .needleColor(Tile.FOREGROUND)
                .barColor(Tile.BLUE)
                .barBackgroundColor(Tile.BACKGROUND.darker())
                .tickLabelColor(Tile.FOREGROUND)
                .majorTickMarkColor(Tile.FOREGROUND)
                .minorTickMarkColor(Tile.FOREGROUND)
                .mediumTickMarkColor(Tile.FOREGROUND)
                .build();

        ramGauge.setBarColor(Tile.FOREGROUND);
        ramGauge.setSections(new Section(66, 100, Tile.BLUE));
        MainRamUsage  = TileBuilder.create()
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .skinType(Tile.SkinType.CUSTOM)
                .title("Ram Usage")
                .titleAlignment(TextAlignment.CENTER)
                .graphic(ramGauge)
                .build();
    }
}
