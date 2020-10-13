package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;

import static javafx.scene.paint.Color.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final    double TILE_WIDTH  = 320;
    private static final    double TILE_HEIGHT = 300;

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        Gauge ramGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.SIMPLE_SECTION)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .animated(true)
                .title("RAM Usage")
                .unit("\u00B0%")
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
        Tile MainRamUsage  = TileBuilder.create()
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .skinType(SkinType.CUSTOM)
                .title("Medusa SimpleSection")
                .text("Temperature")
                .graphic(ramGauge)
                .build();


        var gridPane = new GridPane();

        EventHandler<MouseEvent> gridEventClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Node source = (Node)e.getSource();
                System.out.println("Clicked");
                var column = GridPane.getColumnIndex(source);
                var row = GridPane.getRowIndex(source);
                System.out.printf("Row: %d",row);
                System.out.printf("Column: %d",column);
            }
        };

        CPU cpu = new CPU(TILE_WIDTH,TILE_HEIGHT);

        MainRamUsage.addEventHandler(MouseEvent.MOUSE_CLICKED,gridEventClickHandler);
        gridPane.add(cpu.MainCpuTemp,1,0);
        gridPane.add(MainRamUsage,1,1);

        gridPane.setHgap(20);
        gridPane.setVgap(10);

        Background mainback = new Background(new BackgroundFill(Tile.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY));
        gridPane.setBackground(mainback);
        gridPane.setPrefSize(1024,600);

        var scene = new Scene(gridPane, 1024, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
        

    }

}