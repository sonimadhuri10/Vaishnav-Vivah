package com.ziasy.vaishnavvivah.model;

public class GridModel {

    int Image;
    String name, age;

    public GridModel(int image, String name, String age) {
        Image = image;
        this.name = name;
        this.age = age;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
