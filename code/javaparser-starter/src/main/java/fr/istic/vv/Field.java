package fr.istic.vv;

import java.util.ArrayList;

public class Field {
    public String name;
    public int frequency;

    public Field(String name) {
        this.name = name;
        this.frequency = 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
