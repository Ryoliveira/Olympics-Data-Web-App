package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class SportsList {

    private List<Sport> sports;

    public SportsList() {
    }

    public SportsList(List<Sport> sports) {
        this.sports = sports;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

    @Override
    public String toString() {
        return "SportsList{" +
                "sports=" + sports +
                '}';
    }
}
