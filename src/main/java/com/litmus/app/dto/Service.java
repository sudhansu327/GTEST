package com.litmus.app.dto;

import java.util.Map;

public class Service {

    private Map<String, Silo> silos;

    private Map<String, Operation> operations;

    public Map<String, Operation> getOperations() {
        return operations;
    }

    public void setOperations(Map<String, Operation> operations) {
        this.operations = operations;
    }

    public Map<String, Silo> getSilos() {
        return silos;
    }

    public void setSilos(Map<String, Silo> silos) {
        this.silos = silos;
    }
}
