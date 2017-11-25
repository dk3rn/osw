/*
 * This Class is part of the Model (MVC)
 */
package de.dk3rn.osw;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nuke
 */
public class OsFunctions {
    
    
    
    /**
     * This method is used to set the openspot dynamicall to a certain TG.This is esspecially interesting for TG 4000 to disconnect the Openspot 
 from all TGs.
     * 
     * @param TG - TalkGroup which should be added dynamically to the Openspot
     * @param type
     * @return executePost - String containing the Openspot Response 
     */
    public String quickCall(int TG, int type){ 
        String executePost = null;
        try {
            ConfigX conf = ConfigX.getInstance();
            executePost = conf.getHttph().executePost("{\"dst_id\": "+TG+", \"call_type\":" +   type  + ",\"tdma_channel\": 0}","http://"+ conf.getIp() + "/quickcall.cgi", conf.getJwt());
            System.out.println(executePost);
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return executePost;      
    }
    
    
    public String reRouting(int TG){
        String executePost = null;
        try {
            ConfigX conf = ConfigX.getInstance();
            executePost = conf.getHttph().executePost("{\"reroute_id\": "+ TG +"}","http://"+ conf.getIp() + "/homebrewsettings.cgi", conf.getJwt());
            System.out.println(executePost);
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return executePost;
    }
    
    
    
    public String status(){

        String executePost = null;
        try {
            ConfigX conf = ConfigX.getInstance();
            executePost = conf.getHttph().executePost("", "http://" + conf.getIp() + "/status.cgi", conf.getJwt());
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return executePost;
    }
}

