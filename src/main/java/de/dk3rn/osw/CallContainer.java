/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk3rn.osw;

/**
 *
 * @author Nuke
 */
public class CallContainer {
    
    // ---- Constructors ----
    public CallContainer(){}
    public CallContainer(int dmrid, int type, String caption){
        this.caption = caption;
        this.dmrid = dmrid;
        this.type = type;
    }
    

    // ---- Class Variables including Getter/Setter ----
    private int dmrid; // TG or Reflector Number
    private int type; // 0: Group Call, 1: Private Call
    private String caption; // Caption of the button

    // --- 

    public int getDmrid() {
        return dmrid;
    }

    public void setDmrid(int dmrid) {
        this.dmrid = dmrid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


}
