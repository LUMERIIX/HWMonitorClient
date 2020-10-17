package org.example;

import java.util.*;

public class Hardware {

    public CPU cpu;
    public GPU gpu;
    public Motherboard mb;
    public RAM ram;
    public Storage storage;
    public FanController fanController;
    public NetworkInterface networkInterface;

    public Hardware ()
    {
        cpu = new CPU();
        gpu = new GPU();
        mb = new Motherboard();
        ram = new RAM();
        storage = new Storage();
        fanController = new FanController();
        networkInterface = new NetworkInterface();
    }

}