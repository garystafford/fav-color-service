package com.example.color;

import java.util.Arrays;
import java.util.List;

final class ColorList {

    public static List<String> colors = Arrays.asList(
            "green",
            "red",
            "yellow",
            "blue",
            "orange",
            "purple",
            "gray",
            "white",
            "black"
    );

    public static List<String> getColors() {
        return colors;
    }
}
