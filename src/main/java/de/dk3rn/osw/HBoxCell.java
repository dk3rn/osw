/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk3rn.osw;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Nuke
 */
public class HBoxCell extends HBox {

    Label lbl_name = new Label();
    Label lbl_dmrid = new Label();
    Label lbl_type = new Label();
    
    public int dmrid; // TG or Reflector Number
    public int type; // 0: Group Call, 1: Private Call
    public String name; // Caption of the button

    HBoxCell(String name, int dmrid, int type) {
        super();

        this.name = name;
        this.dmrid = dmrid;
        this.type = type;
        
        
        lbl_name.setText("Name: "+ name);
        lbl_dmrid.setText(" --- DMRID: " + Integer.toString(dmrid));
        
        String calltype;
        if (type == 0){
            calltype = "Group Call";
        }
        else
        {
            calltype = "Private Call";
        }
        
        lbl_type.setText(" --- Calltype: " +calltype);


       this.getChildren().addAll(lbl_name,lbl_dmrid,lbl_type);
    }
}
