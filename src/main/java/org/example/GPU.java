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
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GPU
{
    //Data
    public String Name;
    public ArrayList<DataStruct> Clock;
    public ArrayList<DataStruct> Load;
    public double Temperature;
    public double TotalMemory;
    public double AvailableMemory;
    public int FanSpeed;
    public double Power;

    //Visual
    public Tile MainGpuTemp;
    public Scene backgroundScene;
    private GridPane gridPane;
    private Tile FanSpeedTile;
    private Tile PowerTile;
    private Tile LoadCoreTile;
    private Tile LoadMemoryTile;
    private Tile ClockTile;
    private Tile GPUCoreTempTile;
    private VBox LoadTable = new VBox();
    private VBox ClkTable = new VBox();
    private Stage stage = new Stage();
    private String[] LoadTableColumns = {"Load","Value"};
    private String[] ClockTableColumns = {"Clock","Value"};

    public GPU()
    {
        Name = new String();
        Clock = new ArrayList<DataStruct>();
        Load = new ArrayList<DataStruct>();
        createMainTile();
    }

    public void updateBackground ()
    {
        gridPane.getChildren().clear();
        LoadTable.getChildren().clear();
        ClkTable.getChildren().clear();
        LoadTable = Dashboard.createComponentTable(LoadTableColumns,DataStruct.extractDataStructName(Load),DataStruct.extractDataStructVal(Load),"%");
        ClkTable = Dashboard.createComponentTable(ClockTableColumns,DataStruct.extractDataStructName(Clock),DataStruct.extractDataStructVal(Clock),"MHz");
        MainGpuTemp.setValue(Temperature);
        GPUCoreTempTile.setValue(Temperature);
        PowerTile.setValue(Power);
        LoadCoreTile.setValue(Load.get(0).val);
        LoadMemoryTile.setValue(Load.get(3).val);
        ClockTile.setValue(Clock.get(0).val);
        FanSpeedTile.setValue(FanSpeed);
        gridPane.add(GPUCoreTempTile,0,0);
        gridPane.add(PowerTile,1,1);
        gridPane.add(FanSpeedTile,0,1);
        gridPane.add(LoadMemoryTile,1,0);
        gridPane.add(LoadCoreTile,2,0);
        gridPane.add(LoadTable,2,1);
        gridPane.add(ClockTile,3,0);
        gridPane.add(ClkTable,3,1);
        clearDataStructs();
    }

    public void clearDataStructs ()
    {
        Load.clear();
        Clock.clear();
    }

    private EventHandler<MouseEvent> MainViewGpuHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e)
        {
            if(gridPane == null)
                initBackground();
            Node source = (Node)e.getSource();
            System.out.println("GPU Main handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
            stage.show();
        }
    };

    private void initBackground()
    {
        GPUCoreTempTile = TileBuilder.create()
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

        FanSpeedTile = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("GPU Fan Speed")
                .value(0)
                .unit("RPM")
                .build();

        PowerTile = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("Power")
                .value(0)
                .unit("W")
                .build();

        LoadCoreTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("GPU Core Load")
                .titleAlignment(TextAlignment.CENTER)
                .unit("%")
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

        LoadMemoryTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("GPU Memory Load")
                .titleAlignment(TextAlignment.CENTER)
                .unit("%")
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

        ClockTile = TileBuilder.create()
                .skinType(Tile.SkinType.SPARK_LINE)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("GPU Core Clock")
                .unit("MHz")
                .gradientStops(new Stop(0, Tile.GREEN),
                        new Stop(4300, Tile.YELLOW),
                        new Stop(4800, Tile.RED))
                .strokeWithGradient(true)
                //.smoothing(true)
                .build();

        gridPane = Dashboard.createDashboardGridPane();

        LoadTable = new VBox();
        ClkTable = new VBox();

        gridPane.add(GPUCoreTempTile,0,0);
        gridPane.add(PowerTile,1,1);
        gridPane.add(FanSpeedTile,0,1);
        gridPane.add(LoadMemoryTile,1,1);
        gridPane.add(LoadCoreTile,2,0);
        gridPane.add(LoadTable,2,1);
        gridPane.add(ClockTile,3,0);
        gridPane.add(ClkTable,3,1);

        GPUCoreTempTile.addEventHandler(MouseEvent.MOUSE_CLICKED,BackgroundHandler);

        backgroundScene = new Scene(gridPane, 1024, 600);
        gridPane.setGridLinesVisible(true);

        stage.setScene(backgroundScene);
    }

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

    private void createMainTile()
    {
        MainGpuTemp = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("GPU Temp")
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

        MainGpuTemp.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewGpuHandler);
    }
}
