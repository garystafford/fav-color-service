package com.example.color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class ColorList {

    static List<String> colors = Arrays.asList(
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

    static List<String> getColors() {
        List<String> colorSorted = colors.subList(1, colors.size());
        Collections.sort(colorSorted);
        return colorSorted;
    }
}
