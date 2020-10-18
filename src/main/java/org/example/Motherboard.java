package org.example;

import java.util.ArrayList;

public class Motherboard
{
    public String Name;
    public ArrayList<Float> Temperature;
    public double CoreVoltage;
    public double BatteryVoltage;
    public double Voltage3V3;

    public Motherboard()
    {
        Name = new String();
        Temperature  = new ArrayList <Float>();
    }
}
