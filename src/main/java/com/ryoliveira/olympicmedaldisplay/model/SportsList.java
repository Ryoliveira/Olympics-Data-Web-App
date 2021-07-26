package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class SportsList {

    private List<String> sports;

    public SportsList(List<String> sports) {
        this.sports = sports;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    @Override
    public String toString() {
        return "SportsList{" +
                "sports=" + sports +
                '}';
    }
}
