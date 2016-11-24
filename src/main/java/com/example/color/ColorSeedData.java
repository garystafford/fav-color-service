package com.example.color;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ColorSeedData {

    private List<Color> choices = new ArrayList<>();

    public ColorSeedData() {
    }

    public List<Color> getColors() {
        return choices;
    }

    // accepts map of colors and total colors
    public void colorsFromMap(Map colors) {
        for (Object key : colors.keySet()) {
            int value = Integer.parseInt(String.valueOf(colors.get(key)));
            for (int i = 0; i < value; i++) {
                String color = String.valueOf(key);
                choices.add(new Color(color));
            }
        }
    }

    // generates random number of total choices for each color
    public void setRandomColors() {
        Map colors = new HashMap();
        List<String> colorList = ColorList.getColors();
        for (int i = 0; i < colorList.size(); i++) {
            colors.put(colorList.get(i), getRandomIntAsString(5, 15));
        }
        colorsFromMap(colors);
    }

    // returns random number as string
    private String getRandomIntAsString(int min, int max) {
        int randomColorCount = ThreadLocalRandom.current().nextInt(min, max + 1);
        return Integer.toString(randomColorCount);
    }
}
