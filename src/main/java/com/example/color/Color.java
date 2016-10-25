package com.example.color;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Color {

    @Id
    private String id;

    private String color;

    Color() {
    }

    Color(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("Color[id=%s, name='%s']", id, getColor());
    }
}
