package com.cyt.appsdk.model;


import com.cyt.appsdk.model.monitor.Monitor;
import com.cyt.appsdk.model.monitor.emevent.EMEvent;

import java.util.ArrayList;

public class AdValue {

    public String resourceID;
    public String adid;
    public String resource;
    public String thumb;
    public ArrayList<Monitor> startMonitor;
    public ArrayList<Monitor> middleMonitor;
    public ArrayList<Monitor> endMonitor;
    public String clickUrl;
    public ArrayList<Monitor> clickMonitor;
    public EMEvent event;
    public String type;
}
