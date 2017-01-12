/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author TErik
 */
public class DTOHund {
    public int id;
    public String namn;
    public String ras;
    public String bildURL; //Här ska vi inte lägga public Image bildURL, för man vill inte fastna i Javafx för databasen. Därför vill vi inte använda objekt som är låsta till JavaFX (Image-objekt). I sådant fall så låser v fast vår applikation i ett programmeringsspråk.
    //public SimpleBooleanProperty iHundgård;

    
    public DTOHund(){}
    public DTOHund(int id, String namn, String ras, String bildURL) {
        this.id = id;
        this.namn = namn;
        this.ras = ras;
        this.bildURL = bildURL;
        //this.iHundgård = iHundgård;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getRas() {
        return ras;
    }

    public void setRas(String ras) {
        this.ras = ras;
    }

    public String getBildURL() {
        return bildURL;
    }

    public void setBildURL(String bildURL) {
        this.bildURL = bildURL;
    }

    /*public SimpleBooleanProperty isiHundgård() {
        return iHundgård;
    }

    public void setiHundgård(SimpleBooleanProperty iHundgård) {
        this.iHundgård = iHundgård;
    }*/
    
}
