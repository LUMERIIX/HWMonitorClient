package org.example;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class CPU
{
    //Data
    public String Name;
    public ArrayList<DataStruct> Clock;
    public ArrayList <DataStruct> Load;
    public int Cores;
    public ArrayList<DataStruct> Temperature;
    public ArrayList<DataStruct> Power;
    public ArrayList<DataStruct> Voltage;

    //Visual
    public Tile MainCpuTemp;    //Core
    public Tile CoreTempTile;
    public Tile PowerTile;
    public Tile LoadTile;
    public Tile ClockTile;
    public VBox CpuTempTable = new VBox();
    public VBox VoltageTable = new VBox();
    public VBox LoadTable = new VBox();
    public VBox ClkTable = new VBox();
    public String[] TempTableColumns = {"Temperature","Value"};
    public String[] VoltageTableColumns = {"Voltage","Value"};
    public String[] LoadTableColumns = {"Load","Value"};
    public String[] ClockTableColumns = {"Clock","Value"};


    private String CoreTempNames[] = {"Core1","Core2"};
    private VBox CoreTempTable;
    private Stage stage = new Stage();
        //dataTable.setFillWidth(true);

    public CPU()
    {
        Name = new String();
        Load = new ArrayList<DataStruct>();
        Clock = new ArrayList<DataStruct>();
        Temperature = new ArrayList<DataStruct>();
        Power = new ArrayList<DataStruct>();
        Voltage = new ArrayList<DataStruct>();
        Power = new ArrayList<DataStruct>();
        initBackground(CoreTempNames,2);
        createMainTile();
    }

    private Scene backgroundScene;

    private GridPane gridPane;

    private void initBackground(String CoreTempNames[],int NrOfCores)
    {
        CoreTempTile = TileBuilder.create()
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

        PowerTile = TileBuilder.create().skinType(Tile.SkinType.CHARACTER)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("Power")
                .titleAlignment(TextAlignment.CENTER)
                .description("-")
                .descriptionAlignment(Pos.CENTER)
                .build();

        PowerTile = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("Power")
                .value(0)
                .unit("W")
                .build();

        LoadTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(Dashboard.TILE_WIDTH, Dashboard.TILE_WIDTH)
                .title("CPU Total Load")
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
                .title("Clock #1")
                .unit("MHz")
                .gradientStops(new Stop(0, Tile.GREEN),
                        new Stop(4300, Tile.YELLOW),
                        new Stop(4800, Tile.RED))
                .strokeWithGradient(true)
                //.smoothing(true)
                .build();

        gridPane = Dashboard.createDashboardGridPane();

        String[] test = {"Temp","Value"};
        String[] test1 = {"Core1","Core2"};
        int[] test2 = {42,51};

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

        var scene = new Scene(gridPane, 1024, 600);
        gridPane.setGridLinesVisible(true);

        stage.setScene(scene);
    }

    private EventHandler<MouseEvent> MainViewCpuHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node source = (Node)e.getSource();
            System.out.println("CPU Main handle");
            var column = GridPane.getColumnIndex(source);
            var row = GridPane.getRowIndex(source);
            System.out.printf("Row: %d",row);
            System.out.printf("Column: %d",column);
            stage.show();
        }
    };

    private void createMainTile()
    {
        MainCpuTemp = TileBuilder.create()
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

        MainCpuTemp.addEventHandler(MouseEvent.MOUSE_CLICKED,MainViewCpuHandler);
    }

}
