package com.example.myapplication;

public class MESSAGES {

    int ID;
    String MESSAGE;
    String MSG_CAT;
    int FAV;

    public MESSAGES(int ID, String MESSAGE, String MSG_CAT, int FAV) {
        this.ID = ID;
        this.MESSAGE = MESSAGE;
        this.MSG_CAT = MSG_CAT;
        this.FAV = FAV;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getMSG_CAT() {
        return MSG_CAT;
    }

    public void setMSG_CAT(String MSG_CAT) {
        this.MSG_CAT = MSG_CAT;
    }

    public int getFAV() {
        return FAV;
    }

    public void setFAV(int FAV) {
        this.FAV = FAV;
    }
}
