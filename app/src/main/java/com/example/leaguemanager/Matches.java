package com.example.leaguemanager;

public class Matches {
    private String date,t1,t2;

    public Matches(String date, String t1, String t2) {
        this.date = date;
        this.t1 = t1;
        this.t2 = t2;
    }
    public Matches(){

    }
    public String getDate() {
        return date;
    }

    public String getT1() {
        return t1;
    }

    public String getT2() {
        return t2;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }
}
