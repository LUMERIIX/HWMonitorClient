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

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");


        var gridPane = Dashboard.createDashboardGridPane();

        CPU cpu = new CPU();
        RAM ram = new RAM();
        GPU gpu = new GPU();
        Storage storage = new Storage();
        Interfaces interfaces = new Interfaces();
        FanController fans = new FanController();


        gridPane.add(cpu.MainCpuTemp,0,0);
        gridPane.add(ram.MainRamUsage,1,0);
        gridPane.add(gpu.MainGpuTemp,2,0);
        gridPane.add(storage.MainStorageUsage,2,1);
        gridPane.add(interfaces.MainInterfaceTile,0,1);
        gridPane.add(fans.MainFanTile,1,1);

        var scene = new Scene(gridPane, 1024, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
        

    }

}