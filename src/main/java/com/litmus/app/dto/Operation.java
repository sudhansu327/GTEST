package com.litmus.app.dto;

public class Operation {

    private String path;
    private String testSuitePath;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTestSuitePath() {
        return testSuitePath;
    }

    public void setTestSuitePath(String testSuitePath) {
        this.testSuitePath = testSuitePath;
    }
}
