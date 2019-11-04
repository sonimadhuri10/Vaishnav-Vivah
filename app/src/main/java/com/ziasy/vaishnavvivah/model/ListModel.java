package com.ziasy.vaishnavvivah.model;

/**
 * Created by ANDROID on 21-Mar-18.
 */

public class ListModel {

    Integer icon;
    String title;

    public ListModel(Integer icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
