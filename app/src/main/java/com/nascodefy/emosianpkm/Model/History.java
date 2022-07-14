package com.nascodefy.emosianpkm.Model;

public class History {

    public String tingkatDiag, dateDiag, skorDiag, anjuranDiag;
    public String uid,username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public History(String tingkatDiag, String dateDiag, String skorDiag, String anjuranDiag) {
        this.tingkatDiag = tingkatDiag;
        this.dateDiag = dateDiag;
        this.skorDiag = skorDiag;
        this.anjuranDiag = anjuranDiag;

    }


    public History() {

    }

    public String getAnjuranDiag() {
        return anjuranDiag;
    }

    public void setAnjuranDiag(String anjuranDiag) {
        this.anjuranDiag = anjuranDiag;
    }

    public String getTingkatDiag() {
        return tingkatDiag;
    }

    public void setTingkatDiag(String tingkatDiag) {
        this.tingkatDiag = tingkatDiag;
    }

    public String getDateDiag() {
        return dateDiag;
    }

    public void setDateDiag(String dateDiag) {
        this.dateDiag = dateDiag;
    }

    public String getSkorDiag() {
        return skorDiag;
    }

    public void setSkorDiag(String skorDiag) {
        this.skorDiag = skorDiag;
    }


}
