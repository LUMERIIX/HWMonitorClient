package org.example;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.scene.text.TextAlignment;


public class RAM
{
    public Tile MainRamUsage;

    public RAM(double TileWidth, double TileHeight)
    {
        createMainTile(TileWidth,TileHeight);
    }

    private void createMainTile(double TileWidth, double TileHeight)
    {
        Gauge ramGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.SIMPLE_SECTION)
                .prefSize(TileWidth, TileHeight)
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
                .prefSize(TileWidth, TileHeight)
                .skinType(Tile.SkinType.CUSTOM)
                .title("Ram Usage")
                .titleAlignment(TextAlignment.CENTER)
                .graphic(ramGauge)
                .build();
    }
}
