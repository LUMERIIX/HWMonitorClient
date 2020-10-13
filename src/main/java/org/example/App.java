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


        var gridPane = new GridPane();

        CPU cpu = new CPU(TILE_WIDTH,TILE_HEIGHT);
        RAM ram = new RAM(TILE_WIDTH,TILE_HEIGHT);
        GPU gpu = new GPU(TILE_WIDTH,TILE_HEIGHT);
        Storage storage = new Storage(TILE_WIDTH,TILE_HEIGHT);

        gridPane.add(cpu.MainCpuTemp,0,0);
        gridPane.add(ram.MainRamUsage,1,0);
        gridPane.add(gpu.MainGpuTemp,2,0);
        gridPane.add(storage.MainStorageUsage,2,1);

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