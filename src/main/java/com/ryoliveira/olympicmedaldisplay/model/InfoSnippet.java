package com.ryoliveira.olympicmedaldisplay.model;

public class InfoSnippet {

    private String label;

    private String text;

    public InfoSnippet(String label, String text) {
        this.label = label;
        this.text = text;
    }

    public InfoSnippet() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "InfoSnippet{" +
                "label='" + label + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
