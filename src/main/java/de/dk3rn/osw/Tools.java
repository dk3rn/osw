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
     
    public ArrayList ObservableListToArrayList(ObservableList oal) {
        ArrayList al = new ArrayList();
        Iterator iter = oal.iterator();
        while (iter.hasNext()) {
            al.add(iter.next());
        }
        return al;
    }
    
    
    public ObservableList ArrayListToObservableList(ArrayList al){
        ObservableList oal = FXCollections.observableArrayList();
        Iterator iter = al.iterator();
        while(iter.hasNext()){
            oal.add(iter.next());
        }
        return oal;
        
    }
     


}
