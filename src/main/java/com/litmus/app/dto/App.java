package com.litmus.app.dto;

import java.util.Map;

public class App {

    private Map<String, Service> services;

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }
}
