package org.example;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.lang.String;
import java.util.ArrayList;

public class Storage
{
    //Data
    public static ArrayList<String> Name;
    public static ArrayList<Double> UsedMemory;
    public static ArrayList<Integer> AvailableMemory;
    public static ArrayList<Double> Temperature;
    public static ArrayList<DataStruct> ThroughputRate;
    public Double TotalDiskMemoryAvailable;
    public Double TotalDiskMemoryUsed;

    public Tile MainStorageUsage;
    public Gauge MainStorageUsageGauge;
    public Scene backgroundScene;
    private static ArrayList<Tile> StorageCapacityTiles;
    private static ArrayList<Gauge> StorageCapacityGauges;
    private GridPane gridPane;
    public VBox TemperatureTable = new VBox();
    public VBox ThroughputTable = new VBox();
    private String[] TemperatureColumns = {"Temperature","Value"};
    private String[] RatesColumns = {"Rates","Value"};
    private Stage stage = new Stage();

    public Storage()
    {
        Name = new ArrayList<String>();
        UsedMemory = new ArrayList<Double>();
        AvailableMemory = new ArrayList<Integer>();
        Temperature = new ArrayList<Double>();
        ThroughputRate = new ArrayList<DataStruct>();
        StorageCapacityTiles = new ArrayList<Tile>();
        StorageCapacityGauges = new ArrayList<Gauge>();
        createMainTile();
    }

    public void initBackground()
    {
        gridPane = Dashboard.createDashboardGridPane();

        for(int i = 0; i < Name.size();i++)
        {
            StorageCapacityGauges.add(createStorageGauge(""));
            StorageCapacityTiles.add(TileBuilder.create()
                    .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                    .skinType(Tile.SkinType.CUSTOM)
                    .title(Name.get(i))
                    .titleAlignment(TextAlignment.CENTER)
                    .graphic(StorageCapacityGauges.get(i))
                    .build());

            gridPane.add(StorageCapacityTiles.get(i),0,i);   //Add all Capacity gauges in one line
        }

        TemperatureTable = new VBox();
        ThroughputTable = new VBox();

        gridPane.add(TemperatureTable,1,0);
        gridPane.add(ThroughputTable,1,1);

        try
        {
            StorageCapacityTiles.get(0).addEventHandler(MouseEvent.MOUSE_CLICKED,BackgroundHandler);
        }
        catch(NullPointerException ex)
        {

        }

        backgroundScene = new Scene(gridPane, 1024, 600);
        gridPane.setGridLinesVisible(true);

        stage.setScene(backgroundScene);
    }

    public void clearDataStructs()
    {
        Name.clear();
        UsedMemory.clear();
        AvailableMemory.clear();
        Temperature.clear();
        ThroughputRate.clear();
    }

    public void updateForeground ()
    {
        TotalDiskMemoryAvailable = 0.0;
        TotalDiskMemoryUsed = 0.0;
        for(int i = 0; i < UsedMemory.size();i++)
        {
            TotalDiskMemoryUsed += UsedMemory.get(i);
        }
        TotalDiskMemoryUsed /= UsedMemory.size();
    }

    public void updateBackground ()
    {
        gridPane.getChildren().clear();
        TotalDiskMemoryAvailable = 0.0;
        TotalDiskMemoryUsed = 0.0;
        for(int i = 0; i < Name.size();i++)
        {
            TotalDiskMemoryUsed += UsedMemory.get(i);
            //TotalDiskMemoryAvailable += AvailableMemory.get(i);
            StorageCapacityGauges.get(i).setValue(/*100/(AvailableMemory.get(i)+UsedMemory.get(i))*UsedMemory.get(i)*/UsedMemory.get(i));
            gridPane.add(StorageCapacityTiles.get(i),i,0);   //Add all Capacity gauges in one line
        }

        TotalDiskMemoryUsed /= Name.size();
        //MainStorageUsage.setValue(100/(TotalDiskMemoryAvailable+TotalDiskMemoryUsed)*TotalDiskMemoryUsed);

        String[] namesArray = new String[Name.size()];
        Double[] temperaturesArray = new Double[Temperature.size()];
        namesArray = Name.toArray(namesArray);
        temperaturesArray = Temperature.toArray(temperaturesArray);
        TemperatureTable.getChildren().clear();
        ThroughputTable.getChildren().clear();
        TemperatureTable = Dashboard.createComponentTable(TemperatureColumns,namesArray,temperaturesArray,"°C");
        ThroughputTable = Dashboard.createComponentTable(RatesColumns,DataStruct.extractDataStructName(ThroughputRate),DataStruct.extractDataStructVal(ThroughputRate),"KB/s");

        gridPane.add(TemperatureTable,0,1);
        gridPane.add(ThroughputTable,1,1);
        clearDataStructs();
    }

    private EventHandler<MouseEvent> BackgroundHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node source = (Node)e.getSource();
            System.out.println("Storage Background handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
            stage.hide();
        }
    };

    private EventHandler<MouseEvent> MainViewStorageHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e)
        {
            if(gridPane == null)
                initBackground();
            /**Node target = (Node);
            Node source = (Node)e.getSource();
            Parent parent = source.getParent();**/
            System.out.println("Storage Main handle");
            var column = GridPane.getColumnIndex(MainStorageUsage);
            var row = GridPane.getRowIndex(MainStorageUsage);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
            stage.show();
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
        MainStorageUsageGauge = createStorageGauge("");
        MainStorageUsage = TileBuilder.create()
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .skinType(Tile.SkinType.CUSTOM)
                .title("Total Disk Usage")
                .titleAlignment(TextAlignment.CENTER)
                .graphic(MainStorageUsageGauge)
                .animated(true)
                .build();
    }
}
