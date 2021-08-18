package com.ryoliveira.olympicmedaldisplay.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "athlete")
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String photoUrl;
    private String country;
    private String countryFlagUrl;
    private String discipline;
    private String dob;
    private int age;
    private String gender;
    private String height;
    private String placeOfBirth;
    private String birthCountry;
    private String placeOfResidence;
    private String residenceCountry;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "misc_info_fid", referencedColumnName = "id")
    private List<AthleteInfoSnippet> additionalInfo;

    public Athlete(String name, String photoUrl, String country, String countryFlagUrl, String discipline, String dob, int age, String gender, String height, String placeOfBirth, String birthCountry, String placeOfResidence, String residenceCountry) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.country = country;
        this.countryFlagUrl = countryFlagUrl;
        this.discipline = discipline;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.placeOfBirth = placeOfBirth;
        this.birthCountry = birthCountry;
        this.placeOfResidence = placeOfResidence;
        this.residenceCountry = residenceCountry;
        this.additionalInfo = new ArrayList<>();
    }

    public Athlete() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public String getResidenceCountry() {
        return residenceCountry;
    }

    public void setResidenceCountry(String residenceCountry) {
        this.residenceCountry = residenceCountry;
    }

    public List<AthleteInfoSnippet> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<AthleteInfoSnippet> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", country='" + country + '\'' +
                ", countryFlagUrl='" + countryFlagUrl + '\'' +
                ", discipline='" + discipline + '\'' +
                ", dob='" + dob + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", height='" + height + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", birthCountry='" + birthCountry + '\'' +
                ", placeOfResidence='" + placeOfResidence + '\'' +
                ", residenceCountry='" + residenceCountry + '\'' +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}

