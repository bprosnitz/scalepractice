package com.example.bprosnitz.scalepractice;

/**
 * Created by bprosnitz on 4/16/16.
 */
public class Exercise {
    public String name;
    public Scale scale;

    public Exercise(String name, Scale scale) {
        this.name = name;
        this.scale = scale;
    }

    public String toString() {
        return this.name;
    }
}
