package com.ryoliveira.olympicmedaldisplay.model;

import javax.persistence.*;

@Entity
@Table(name = "athlete_misc_info")
public class AthleteInfoSnippet{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    private String label;

    @Column(columnDefinition = "TEXT")
    private String text;

    public AthleteInfoSnippet() {
    }

    public AthleteInfoSnippet(String label, String text) {
        this.label = label;
        this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String lable) {
        this.label = lable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
