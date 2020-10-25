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
import com.google.gson.JsonObject;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application
{
    private Hardware hw;
    private Interface interface1;
    private JsonObject json;

    private void sysCycle ()
    {
        try
        {
            json = interface1.OHWMInterface.readJsonFromUrl("http://192.168.1.22:8085/data.json");
        }
        catch(IOException io) {}

        try
        {
            interface1.OHWMInterface.parseJson(json,hw);
        } catch(IOException io) { }

        hw.cpu.MainCpuTemp.setValue(hw.cpu.Temperature.get(0).val);
        hw.ram.ramGauge.setValue(100.0/(hw.ram.AvailableMemory+hw.ram.UsedMemory)*hw.ram.UsedMemory);
        hw.gpu.MainGpuTemp.setValue(hw.gpu.Temperature);
        if(hw.cpu.backgroundScene != null)
            hw.cpu.updateBackground();
        hw.cpu.updateForeground();
        if(hw.gpu.backgroundScene != null)
            hw.gpu.updateBackground();

        hw.gpu.clearDataStructs();
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

    @Override
    public void start(Stage stage)
    {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        var gridPane = Dashboard.createDashboardGridPane();

        hw = new Hardware();
        interface1 = new Interface();

        gridPane.add(hw.cpu.MainCpuTemp,0,0);
        gridPane.add(hw.ram.MainRamUsage,1,0);
        gridPane.add(hw.gpu.MainGpuTemp,2,0);
        gridPane.add(hw.storage.MainStorageUsage,2,1);
        gridPane.add(hw.networkInterface.MainInterfaceTile,0,1);
        gridPane.add(hw.fanController.MainFanTile,1,1);

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