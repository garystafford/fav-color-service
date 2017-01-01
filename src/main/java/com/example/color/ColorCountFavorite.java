package com.example.color;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ColorCountFavorite {

    private int count;

    public ColorCountFavorite() {
    }

    public ColorCountFavorite(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}