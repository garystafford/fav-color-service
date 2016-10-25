package com.example.color;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ColorCount {

    private String color;

    private int count;

    public ColorCount() {
    }

    public ColorCount(String color, int count) {
        this.color = color;
        this.count = count;
    }

    public String getColor() {
        return color;
    }

    public int getCount() {
        return count;
    }
}