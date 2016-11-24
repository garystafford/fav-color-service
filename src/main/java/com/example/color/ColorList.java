package com.example.color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ColorList {

    private static List<String> colors = Arrays.asList(
            "Green",
            "Red",
            "Yellow",
            "Blue",
            "Orange",
            "Purple",
            "Gray",
            "White",
            "Black"
    );

    public static List<String> getColors() {
        List<String> colorSorted = colors.subList(0, colors.size());
        Collections.sort(colorSorted);
        return colorSorted;
    }
}
