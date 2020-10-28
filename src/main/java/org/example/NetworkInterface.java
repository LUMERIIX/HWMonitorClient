package org.example;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NetworkInterface
{
    //Data
    public static ArrayList<String> Name;
    public static ArrayList<Double> UploadSpeed;
    public static ArrayList<Double> DownloadSpeed;
    public static ArrayList<Double> UploadedData;
    public static ArrayList<Double> DownloadedData;
    private Integer numOfInterfaces = 0;

    public Tile MainInterfaceTile;
    private Stage stage = new Stage();

    public Scene backgroundScene;

    private GridPane gridPane;


    public NetworkInterface()
    {
        Name = new ArrayList<String>();
        UploadSpeed = new ArrayList<Double>();
        DownloadSpeed = new ArrayList<Double>();
        UploadedData = new ArrayList<Double>();
        DownloadedData = new ArrayList<Double>();
        createMainTile();
    }

    public void updateForeground ()
    {

    }

    public void updateBackground ()
    {
        /**gridPane.getChildren().clear();
        for(int i = 0; i < Name.size();i++)
        {
            TotalDiskMemoryUsed += UsedMemory.get(i);
            //TotalDiskMemoryAvailable += AvailableMemory.get(i);
            StorageCapacityGauges.get(i).setValue(/*100/(AvailableMemory.get(i)+UsedMemory.get(i))*UsedMemory.get(i)*//*UsedMemory.get(i));*/
            //gridPane.add(StorageCapacityTiles.get(i),i,0);   //Add all Capacity gauges in one line
        //}
    }

    private void clearDataStructs ()
    {
        Name.clear();
        UploadSpeed.clear();
        DownloadSpeed.clear();
        UploadedData.clear();
        DownloadedData.clear();
    }

    private void initBackground()
    {
        /*CoreTempTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("CPU Temp")
                .titleAlignment(TextAlignment.CENTER)
                .unit("\u00B0C")
                .animated(true)
                .textVisible(false)
                .averagingPeriod(25)
                .autoReferenceValue(true)
                .barColor(Tile.YELLOW_ORANGE)
                .barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
                .sections(new eu.hansolo.tilesfx.Section(0, 33, Tile.LIGHT_GREEN),
                        new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                        new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
                .sectionsVisible(true)
                .highlightSections(true)
                .strokeWithGradient(true)
                .gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
                        new Stop(0.33, Tile.LIGHT_GREEN),
                        new Stop(0.33,Tile.YELLOW),
                        new Stop(0.67, Tile.YELLOW),
                        new Stop(0.67, Tile.LIGHT_RED),
                        new Stop(1.0, Tile.LIGHT_RED))
                .build();

        gridPane = Dashboard.createDashboardGridPane();

        CpuTempTable = new VBox();
        VoltageTable = new VBox();
        LoadTable = new VBox();
        ClkTable = new VBox();

        gridPane.add(CoreTempTile,0,0);
        gridPane.add(CpuTempTable,0,1);
        gridPane.add(VoltageTable,1,0);
        gridPane.add(PowerTile,1,1);
        gridPane.add(LoadTile,2,0);
        gridPane.add(LoadTable,2,1);
        gridPane.add(ClockTile,3,0);
        gridPane.add(ClkTable,3,1);

        //CoreTempTile.addEventHandler(MouseEvent.MOUSE_CLICKED,BackgroundHandler);

        backgroundScene = new Scene(gridPane, 1024, 600);
        gridPane.setGridLinesVisible(true);

        stage.setScene(backgroundScene);*/
    }

    private EventHandler<MouseEvent> MainViewInterfaceHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e)
        {
            if(gridPane == null)
                initBackground();
            Node source = (Node)e.getSource();
            System.out.println("CPU Main handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
            stage.show();
        }
    };

    private EventHandler<MouseEvent> BackgroundHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node source = (Node)e.getSource();
            System.out.println("CPU Background handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
            stage.hide();
        }
    };

    private void createMainTile()    //shows only nr of available network interfaces
    {
        MainInterfaceTile = TileBuilder.create().skinType(Tile.SkinType.CHARACTER)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("Interfaces")
                .titleAlignment(TextAlignment.CENTER)
                .description("-")
                .build();

        MainInterfaceTile.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewInterfaceHandler);
    }
}
