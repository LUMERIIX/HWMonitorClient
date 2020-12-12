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
    public ArrayList<DataStruct> Name;
    public ArrayList<DataStruct> UploadSpeed;
    public ArrayList<DataStruct> DownloadSpeed;
    public ArrayList<DataStruct> UploadedData;
    public ArrayList<DataStruct> DownloadedData;
    public ArrayList<DataStruct> NetworkUtilization;
    private Integer numOfInterfaces = 0;

    public Tile MainInterfaceTile;
    private Stage stage = new Stage();

    public Scene backgroundScene;

    private GridPane gridPane;


    public NetworkInterface()
    {
        Name = new ArrayList<DataStruct>();
        UploadSpeed = new ArrayList<DataStruct>();
        DownloadSpeed = new ArrayList<DataStruct>();
        UploadedData = new ArrayList<DataStruct>();
        DownloadedData = new ArrayList<DataStruct>();
        NetworkUtilization = new ArrayList<DataStruct>();
        InterfaceTable = new ArrayList<VBox>();
        InterfaceUsage = new ArrayList<Tile>();
        createMainTile();
    }

    public void updateForeground ()
    {
        numOfInterfaces = Name.size();
        if(numOfInterfaces != null)
            MainInterfaceTile.setDescription(numOfInterfaces.toString());
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

    private VBox createNetworkInterfaceTable (int interfaceIndex)
    {
        List<HBox> hBoxList = new ArrayList<HBox>();
        VBox table = new VBox();

        String[] VarNames = {DownloadedData.get(interfaceIndex).name.toString(),UploadedData.get(interfaceIndex).name.toString(),DownloadSpeed.get(interfaceIndex).name.toString(),UploadSpeed.get(interfaceIndex).name.toString()};
        String[] Values = {DownloadedData.get(interfaceIndex).val.toString(),UploadedData.get(interfaceIndex).val.toString(),DownloadSpeed.get(interfaceIndex).val.toString(),UploadSpeed.get(interfaceIndex).val.toString()};
        String[] Units = {DownloadedData.get(interfaceIndex).unit,UploadedData.get(interfaceIndex).unit,DownloadSpeed.get(interfaceIndex).unit,UploadSpeed.get(interfaceIndex).unit};


        Label[] valueNameLabels = new Label[VarNames.length];
        Label[] valueLabels = new Label[VarNames.length];
        Label[] unitLabels = new Label[VarNames.length+1];

        Region spacer = new Region();
        spacer.setPrefSize(5,5);


        table.setPrefSize(Dashboard.TILE_WIDTH,Dashboard.TILE_HEIGHT);

        Label columnLabelLeft = new Label(TemperatureColumns[0]);
        Label columnLabelRight = new Label(TemperatureColumns[1]);


        columnLabelLeft.setAlignment(Pos.CENTER_LEFT);
        columnLabelLeft.setTextFill(Dashboard.Text);
        columnLabelRight.setAlignment(Pos.CENTER_RIGHT);
        columnLabelRight.setTextFill(Dashboard.Text);


        valueNameLabels = Dashboard.createLabelArray(VarNames.length,Pos.CENTER_LEFT,VarNames);
        valueLabels = Dashboard.createLabelArray(Values.length,Pos.CENTER_RIGHT,Values);
        unitLabels = Dashboard.createLabelArray(Units.length,Pos.CENTER_RIGHT,Units);

        hBoxList.add(Dashboard.createHBox(columnLabelLeft,columnLabelRight));

        for(int i = 0; i < VarNames.length; i++)
        {
            HBox hbox = Dashboard.createHBox(valueNameLabels[i],valueLabels[i],unitLabels[i]);
            hBoxList.add(hbox);
        }

        for(int i = 0; i < hBoxList.size(); i++)
            table.getChildren().add(hBoxList.get(i));

        return table;
    }

    public void clearDataStructs ()
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
                .description(numOfInterfaces.toString())
                .build();

        MainInterfaceTile.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewInterfaceHandler);
    }
}
