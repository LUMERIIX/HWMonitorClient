package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.util.Timer;

/**
 * JavaFX App
 */
public class App extends Application {

    private long lastTimerCall;
    private Timer timer;
    private long test = 0;

    private void sysCycle ()
    {
        test = test + 10;
        cpu.MainCpuTemp.setValue(test);
    }

    private Timeline processTimeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    System.out.println("this is called every 1 seconds on UI thread");
                    sysCycle();
                }
            }
        )
    );

    private CPU cpu;
    private RAM ram;
    private GPU gpu;

    @Override
    public void start(Stage stage)
    {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        var gridPane = Dashboard.createDashboardGridPane();

        cpu = new CPU();
        ram = new RAM();
        gpu = new GPU();
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

        processTimeline.setCycleCount(Timeline.INDEFINITE);
        processTimeline.play();
    }

    public static void main(String[] args)
    {
        launch();
    }

}