package com.ryoliveira.olympicmedaldisplay.model;

import java.util.*;

public class CountryInformation {

    private int rank;

    private int totalRank;

    private List<SubMedalStanding> medalsByDivision;

    private List<SubMedalStanding> medalsBySport;

    private List<SubMedalStanding> medalsByYear;

    private InfoSnippet highlights;

    private List<InfoSnippet> links;

    private List<InfoSnippet> anthemInfo;

    private List<Athlete> flagbearerInfo;

    private List<InfoSnippet> membershipInfo;

    private List<InfoSnippet> officialsInfo;

    private List<InfoSnippet> participationInfo;

    public CountryInformation() {
    }

    public CountryInformation(int rank, int totalRank, List<SubMedalStanding> medalsByDivision, List<SubMedalStanding> medalsBySport, List<SubMedalStanding> medalsByYear, InfoSnippet highlights, List<InfoSnippet> links, List<InfoSnippet> anthemInfo, List<Athlete> flagbearerInfo, List<InfoSnippet> membershipInfo, List<InfoSnippet> officialsInfo, List<InfoSnippet> participationInfo) {
        this.rank = rank;
        this.totalRank = totalRank;
        this.medalsByDivision = medalsByDivision;
        this.medalsBySport = medalsBySport;
        this.medalsByYear = medalsByYear;
        this.highlights = highlights;
        this.links = links;
        this.anthemInfo = anthemInfo;
        this.flagbearerInfo = flagbearerInfo;
        this.membershipInfo = membershipInfo;
        this.officialsInfo = officialsInfo;
        this.participationInfo = participationInfo;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTotalRank() {
        return totalRank;
    }

    public void setTotalRank(int totalRank) {
        this.totalRank = totalRank;
    }

    public List<SubMedalStanding> getMedalsByDivision() {
        return medalsByDivision;
    }

    public void setMedalsByDivision(List<SubMedalStanding> medalsByDivision) {
        this.medalsByDivision = medalsByDivision;
    }

    public List<SubMedalStanding> getMedalsBySport() {
        return medalsBySport;
    }

    public void setMedalsBySport(List<SubMedalStanding> medalsBySport) {
        this.medalsBySport = medalsBySport;
    }

    public List<SubMedalStanding> getMedalsByYear() {
        return medalsByYear;
    }

    public void setMedalsByYear(List<SubMedalStanding> medalsByYear) {
        this.medalsByYear = medalsByYear;
    }

    public InfoSnippet getHighlights() {
        return highlights;
    }

    public void setHighlights(InfoSnippet highlights) {
        this.highlights = highlights;
    }

    public List<InfoSnippet> getLinks() {
        return links;
    }

    public void setLinks(List<InfoSnippet> links) {
        this.links = links;
    }

    public List<InfoSnippet> getAnthemInfo() {
        return anthemInfo;
    }

    public void setAnthemInfo(List<InfoSnippet> anthemInfo) {
        this.anthemInfo = anthemInfo;
    }

    public List<Athlete> getFlagbearerInfo() {
        return flagbearerInfo;
    }

    public void setFlagbearerInfo(List<Athlete> flagbearerInfo) {
        this.flagbearerInfo = flagbearerInfo;
    }

    public List<InfoSnippet> getMembershipInfo() {
        return membershipInfo;
    }

    public void setMembershipInfo(List<InfoSnippet> membershipInfo) {
        this.membershipInfo = membershipInfo;
    }

    public List<InfoSnippet> getOfficialsInfo() {
        return officialsInfo;
    }

    public void setOfficialsInfo(List<InfoSnippet> officialsInfo) {
        this.officialsInfo = officialsInfo;
    }

    public List<InfoSnippet> getParticipationInfo() {
        return participationInfo;
    }

    public void setParticipationInfo(List<InfoSnippet> participationInfo) {
        this.participationInfo = participationInfo;
    }

    @Override
    public String toString() {
        return "CountryInformation{" +
                "rank=" + rank +
                ", totalRank=" + totalRank +
                ", medalsByDivision=" + medalsByDivision +
                ", medalsBySport=" + medalsBySport +
                ", medalsByYear=" + medalsByYear +
                ", highlights=" + highlights +
                ", links=" + links +
                ", anthemInfo=" + anthemInfo +
                ", flagbearerInfo=" + flagbearerInfo +
                ", membershipInfo=" + membershipInfo +
                ", officialsInfo=" + officialsInfo +
                ", participationInfo=" + participationInfo +
                '}';
    }
}
