package com.nascodefy.emosianpkm.Model;

public class Journal {

    public String id;
    public String titleFeel;
    public String descFeel;
    public String dateFeel;
    public String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Journal(String id, String titleFeel, String descFeel, String dateFeel, String username) {
        this.id = id;
        this.titleFeel = titleFeel;
        this.descFeel = descFeel;
        this.dateFeel = dateFeel;
        this.username =username;
    }

    public Journal() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleFeel() {
        return titleFeel;
    }

    public void setTitleFeel(String titleFeel) {
        this.titleFeel = titleFeel;
    }

    public String getDescFeel() {
        return descFeel;
    }

    public void setDescFeel(String descFeel) {
        this.descFeel = descFeel;
    }

    public String getDateFeel() {
        return dateFeel;
    }

    public void setDateFeel(String dateFeel) {
        this.dateFeel = dateFeel;
    }


}