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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     


}
