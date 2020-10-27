package com.litmus.app.dao;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="app_model", schema="dbo")
public class AppModel implements Serializable {

    @Id
    @Column(name = "app_name")
    private String appName;

    @Column(name = "json_data")
    private String jsonConfig;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getJsonConfig() {
        return jsonConfig;
    }

    public void setJsonConfig(String jsonConfig) {
        this.jsonConfig = jsonConfig;
    }
}
