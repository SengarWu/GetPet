package com.example.administrator.getpet.bean;

import java.io.Serializable;
import java.util.Date;
public class category  implements Serializable {

public String id;
public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
