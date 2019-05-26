package com.example.versionone;

import android.app.Application;

public class GlobalClass extends Application {

    private String urlcolleccted;

    public String getUrlcolleccted() {
        return urlcolleccted;
    }

    public void setUrlcolleccted(String urlcolleccted) {
        this.urlcolleccted = urlcolleccted;
    }
}
