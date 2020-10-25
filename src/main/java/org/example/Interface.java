package org.example;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;

import org.json.JSONException;

class OpenHardwareMonitorInterface {

    public static final int TextPos = 0;
    public static final int ValuePos = 1;


    public static final String PCNAME = "1";
    public static final String MB = "2";
    public static final String CPUTop = "43";
    public static final String CPUEnd = "103";

    public static final String GPUTop = "113";
    public static final String GPUEnd = "138";

    public static final String RamUsedMemory = "109";
    public static final String RamFreeMemory = "110";
    public static final String[] DisksTop = {"139","151","163"};
    public static final String[] DiskLength = {"12","12","20"};
    public static final String[] Interfaces = {"208"};

    public OpenHardwareMonitorInterface()
    {

    }

    public static void getMapArray(JsonArray array, Multimap <String,String> Linkermap)
    {
        for(int i = 0; i < array.size();i++)
        {
            Object value = array.get(i);
            if(value instanceof JsonObject)
            {
                JsonObject obj = (JsonObject) value;
                getMapObject(obj, Linkermap);
            }
            else if(value instanceof JsonArray)
            {
                JsonArray arr = (JsonArray) value;
                getMapArray(arr, Linkermap);
            }
        }
    }

    public static void getMapObject(JsonObject object, Multimap <String,String> Linkermap)
    {
        Set<String> keymap = object.keySet();
        Iterator<String> iterator = keymap.iterator();

        Linkermap.put(object.get("id").getAsString(), object.get("Text").getAsString());
        Linkermap.put(object.get("id").getAsString(), object.get("Value").getAsString());

        while(iterator.hasNext())
        {
            Object value = object.get(iterator.next());

            if(value instanceof JsonObject)
            {
                JsonObject obj = (JsonObject) value;
                getMapObject(obj, Linkermap);
            }
            else if(value instanceof JsonArray)
            {
                JsonArray arr = (JsonArray) value;
                getMapArray(arr, Linkermap);
            }
        }
    }

    public void extractIterationSector(Multimap<String, String> map ,int IterStart, int IterEnd,  ArrayList<Double> arr)
    {
        for(int i = IterStart; i < IterEnd; i++)
        {
            arr.add(ParseUtil.CutSpecialSymbols(Iterables.get(map.get(Integer.toString(i)), ValuePos)));
        }
    }

    /**@Override
    public void extractIterationSector(Multimap<String, String> Linkermap ,int IterStart, int IterEnd,  ArrayList<String> arr)
    {
    for(int i = Integer.parseInt(CPUTop); i < Integer.parseInt(CPUEnd); i++)
    {
    arr.add(ParseUtil.CutSpecialSymbols(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos)));
    }
    }**/

    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JsonObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
            return json;
        } finally {
            is.close();
        }
    }

    public DataStruct parseJsonStage(Multimap<String, String> map, String Key)
    {
        DataStruct struct = new DataStruct();

        struct.name = Iterables.get(map.get(Key), TextPos);
        struct.val = ParseUtil.CutSpecialSymbols(Iterables.get(map.get(Key), ValuePos));

        return struct;
    }

    public void parseJson (JsonObject obj, Hardware hw) throws IOException, JSONException {
        assert(obj.isJsonObject());

        Multimap<String, String> Linkermap = ArrayListMultimap.create();
        getMapObject(obj, Linkermap);

        try
        {
            //CPU
            hw.cpu.Name =  Iterables.get(Linkermap.get(CPUTop), TextPos); // 0 = Text; 1 = Value;
            //extractIterationSector(Linkermap, 68,84,hw.cpu.Load);/*Integer.parseInt(CPUTop, Integer.parseInt(CPUEnd84), hw.cpu.Load);
            for(int i = Integer.parseInt(CPUTop); i < Integer.parseInt(CPUEnd); i++)
            {
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("%"))
                {
                    hw.cpu.Load.add(parseJsonStage(Linkermap,Integer.toString(i)));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("°C"))
                {
                    hw.cpu.Temperature.add(parseJsonStage(Linkermap,Integer.toString(i)));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("V"))
                {
                    hw.cpu.Voltage.add(parseJsonStage(Linkermap,Integer.toString(i)));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("MHz"))
                {
                    hw.cpu.Clock.add(parseJsonStage(Linkermap,Integer.toString(i)));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("W"))
                {
                    hw.cpu.Power.add(parseJsonStage(Linkermap,Integer.toString(i)));
                }
            }
            hw.cpu.Cores = hw.cpu.Load.size()/2;

            //GPU
            hw.gpu.Name =  Iterables.get(Linkermap.get(GPUTop), TextPos); // 0 = Text; 1 = Value;
            for(int i = Integer.parseInt(GPUTop); i < Integer.parseInt(GPUEnd); i++)
            {
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("MHz"))
                {
                    hw.gpu.Clock.add(parseJsonStage(Linkermap,Integer.toString(i)));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("°C"))
                {
                    hw.gpu.Temperature = ParseUtil.CutCharacters(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("%"))
                {
                    hw.gpu.Load.add(parseJsonStage(Linkermap,Integer.toString(i)));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("RPM"))
                {
                    hw.gpu.FanSpeed = (int) ParseUtil.CutCharacters(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos).contains("W"))
                {
                    hw.gpu.Power = ParseUtil.CutCharacters(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), TextPos).contains("Memory Free"))
                {
                    hw.gpu.AvailableMemory = (int) ParseUtil.CutCharacters(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos));
                }
                if(Iterables.get(Linkermap.get(Integer.toString(i)), TextPos).contains("Memory Total"))
                {
                    hw.gpu.TotalMemory = (int) ParseUtil.CutCharacters(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos));
                }
            }

            //RAM
            hw.ram.AvailableMemory = (int) (ParseUtil.CutCharacters(Iterables.get(Linkermap.get(RamFreeMemory), ValuePos))*1000); //MB
            hw.ram.UsedMemory = (int) (ParseUtil.CutCharacters(Iterables.get(Linkermap.get(RamUsedMemory), ValuePos))*1000); //MB

            //Storage
            hw.storage.clearDataStructs();
            for(int k = 0; k < DisksTop.length;k++)
            {
                hw.storage.Name.add(Iterables.get(Linkermap.get(DisksTop[k]), TextPos)); // 0 = Text; 1 = Value;
                for (int i = Integer.parseInt(DisksTop[k]); i < (Integer.parseInt(DisksTop[k])+Integer.parseInt(DiskLength[k])); i++)
                {
                    if(Iterables.get(Linkermap.get(Integer.toString(i)), TextPos).contains("Temperature"))
                    {
                        try
                        {
                            hw.storage.Temperature.add(ParseUtil.CutSpecialSymbols(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos)));
                        }
                        catch(NumberFormatException ex)
                        {

                        }
                    }
                    if(Iterables.get(Linkermap.get(Integer.toString(i)), TextPos).contains("Read Rate"))
                    {
                        hw.storage.ThroughputRate.add(parseJsonStage(Linkermap,Integer.toString(i)));
                        hw.storage.ThroughputRate.get(hw.storage.ThroughputRate.size()-1).name = "RD " + hw.storage.Name.get(k);
                    }
                    if(Iterables.get(Linkermap.get(Integer.toString(i)), TextPos).contains("Write Rate"))
                    {
                        hw.storage.ThroughputRate.add(parseJsonStage(Linkermap,Integer.toString(i)));
                        hw.storage.ThroughputRate.get(hw.storage.ThroughputRate.size()-1).name = "WR " + hw.storage.Name.get(k);
                    }
                    if(Iterables.get(Linkermap.get(Integer.toString(i)), TextPos).contains("Used Space"))
                    {
                        hw.storage.UsedMemory.add(ParseUtil.CutSpecialSymbols(Iterables.get(Linkermap.get(Integer.toString(i)), ValuePos)));
                    }
                }
            }

            //MB
            hw.mb.Name = Iterables.get(Linkermap.get(MB), TextPos);
        }
        catch (NullPointerException ex){}
    }
}

public class Interface {

    public OpenHardwareMonitorInterface OHWMInterface  = new OpenHardwareMonitorInterface();

    public Interface()
    {

    }
}