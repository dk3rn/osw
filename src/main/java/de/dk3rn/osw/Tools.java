/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk3rn.osw;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nuke
 */
public class Tools {
    
     public String extractSubStringFromJSON(String json, String entity_name){
         ObjectMapper mapper = new ObjectMapper();
         Map map = null;
         try {
             map = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
         } catch (IOException ex) {
             Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
         }
         return map.get(entity_name).toString();
     }
     
     public ArrayList extractArrayListFromJSON(String json, String entity_name){
        ObjectMapper mapper = new ObjectMapper();
        Map map = null;
        try {
            map = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
        } catch (IOException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList list = (ArrayList) map.get(entity_name);
        return list;
    }
     
     
    
    public ArrayList<HBoxCell> copyToHBoxList(ArrayList<CallContainer> al){
        
        ArrayList<HBoxCell> hbal = new ArrayList();
        Iterator<CallContainer> iter = al.iterator();
       
        
        while(iter.hasNext()){
            CallContainer cc = iter.next();
            hbal.add(new HBoxCell(cc.getCaption(),cc.getDmrid(),cc.getType()));
        }
        return hbal;
    }
    
    
        public ArrayList<CallContainer> copyHBoxListToList(List hbl){
        
        ArrayList<CallContainer> cc = new ArrayList();
        Iterator<HBoxCell> iter = hbl.iterator();
       
        
        while(iter.hasNext()){
            HBoxCell hbc = iter.next();
            cc.add(new CallContainer(hbc.dmrid, hbc.type, hbc.name));
        }
        return cc;
    }
            
     


}
