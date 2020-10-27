package com.litmus.app.dto;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class AppsRequest {

 private Map<String, App> apps;

    public Map<String, App> getApps() {
        return apps;
    }

    public void setApps(Map<String, App> apps) {
        this.apps = apps;
    }

    public void addApp(String appName, App app) {
        if(StringUtils.isNotBlank(appName) && app != null){
            if(MapUtils.isEmpty(this.apps)) this.apps = new HashMap<>();
            this.apps.put(appName, app);
        }
    }
}
