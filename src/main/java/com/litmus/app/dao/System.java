package com.litmus.app.dao;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="system", schema="dbo")
public class System implements Serializable {
    @Id
    @Column(name = "systemname")
    private String systemName;

    @Column(name = "modules")
    private String modules;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }
}