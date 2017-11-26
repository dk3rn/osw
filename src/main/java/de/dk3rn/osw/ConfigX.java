/* MODEL (MVC) */

package de.dk3rn.osw;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * 
 * This class is part of the MODEL (MVC)
 * @author peterkern
 */



public class ConfigX {
     
    // ----------- Singleton related Stuff -----------------------------------
    private ConfigX() {
    }
    
    public static ConfigX getInstance() {
        return ConfigXHolder.INSTANCE;
    }
    
    private static class ConfigXHolder {
        private static final ConfigX INSTANCE = new ConfigX();
    }
    
 
    // -----------------------------------------------------------------------
    // ---------- Start of Functionality -------------------------------------
    // -----------------------------------------------------------------------
    
    // ---------- Instance Variables -----------------------------------------
    private String password;
    private String dmrid;
    private ReadBrandmeisterTGs rbmTG;

    public ReadBrandmeisterTGs getRbmTG() {
        return rbmTG;
    }

    public void setRbmTG(ReadBrandmeisterTGs rbmTG) {
        this.rbmTG = rbmTG;
    }
    Stage root1;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Stage getRoot1() {
        return root1;
    }

    public void setRoot1(Stage root1) {
        this.root1 = root1;
    }
    

    public String getDmrid() {
        return dmrid;
    }
    private String ip;

    public void setDmrid(String dmrid) {
        this.dmrid = dmrid;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    private String jwt;
    private HttpHandling httph;
    private ArrayList<CallContainer> calls;

    public ArrayList<CallContainer> getCalls() {
        return calls;
    }

    public void setCalls(ArrayList<CallContainer> calls) {
        this.calls = calls;
    }
    private HBox hbox_tgs;
    private FlowPane flowPaneCalls;

    public FlowPane getFlowPaneCalls() {
        return flowPaneCalls;
    }

    public void setFlowPaneCalls(FlowPane flowPaneCalls) {
        this.flowPaneCalls = flowPaneCalls;
    }

    public HBox getHbox_tgs() {
        return hbox_tgs;
    }

    public void setHbox_tgs(HBox hbox_tgs) {
        this.hbox_tgs = hbox_tgs;
    }

    public HttpHandling getHttph() {
        return httph;
    }

    public String getIp() {
        return ip;
    }

    public String getJwt() {
        return jwt;
    }
    

    // ---------- Methods ----------------------------------------------------
    
    
    public void initialize(){
        try {     
            readConConfig();
            this.calls = readCallConfig();

            httph = HttpHandling.getInstance();
            startGenJWT();
            
            // probably more initializing stuff here.
            
        } catch (IOException ex) {
            Logger.getLogger(ConfigX.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void readConConfig() throws IOException{       
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(new File("osw_connection.config"), new TypeReference<Map<String,Object>>(){});
        ip = jsonMap.get("ip").toString();
        password = jsonMap.get("password").toString();
        dmrid = jsonMap.get("dmrid").toString();
        
        
        
    } 
  
//    // get call button with specific fxid
//    public CallContainer getCall(String fxid){
//        Iterator<CallContainer> it = calls.iterator();
//        
//        while(it.hasNext()){
//            CallContainer cc = it.next();
//            if(cc.getFxid().equals(fxid)){
//                return cc;
//            }
//        }
//
//        return null;
//    }



    
    
    // only used once to create initial config file
    // never called during normal operation (this was used during development)
    public void writeConConfig(){  
        ObjectMapper mapper = new ObjectMapper(); 
       
        Map<String, String> jsonMap = new HashMap();
        jsonMap.put("ip", ip);
        jsonMap.put("password", password);
        jsonMap.put("dmrid",dmrid);
        
        try {
            mapper.writeValue(new File("osw_connection.config"), jsonMap);
        } catch (IOException ex) {
            Logger.getLogger(ConfigX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // only used once to create initial config file
    // never called during normal operation (this was used during development)
    public void initialCallConfigWrite(){
        ArrayList<CallContainer> calls = new ArrayList();
        // Add some TGs
        calls.add(new CallContainer(262, 0,"-262--"));
        calls.add(new CallContainer(26200, 0,"-26200--"));
        calls.add(new CallContainer(26299, 0,"-26299--"));

        writeCallConfig(calls);
    }
    
    public void writeCallConfig(ArrayList<CallContainer> calls){  
        ObjectMapper mapper = new ObjectMapper(); 
        try {
            mapper.writeValue(new File("calls.config"), calls);
        } catch (IOException ex) {
            Logger.getLogger(ConfigX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<CallContainer> readCallConfig(){
        ObjectMapper mapper = new ObjectMapper(); 
        ArrayList<CallContainer> calls = null;
        
        try {
            calls = mapper.readValue(new File("calls.config"), new TypeReference<ArrayList<CallContainer>>(){});
        } catch (IOException ex) {
            Logger.getLogger(ConfigX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calls;
    }
    
        
     public void startGenJWT(){
        new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override public void run(){
                      
 
                    genToken();
  
                  }
        },500,1800000); 

     }
     
     
     public void genToken(){
                             try {
                        // get new token from os
                        String jsontoken = httph.executePost("", "http://"+ip+"/gettok.cgi");
                        
                        // extract token
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> jsonMap = mapper.readValue(jsontoken, new TypeReference<Map<String,Object>>(){});
                        String token = jsonMap.get("token").toString();
                         
                        // create Digest
                        String digest =  DigestUtils.sha256Hex(token+password);
                        
                        // get jwt
                        Map<String, String> map = new HashMap<>();
                        
                        map.put("token", token);
                        map.put("digest", digest);
                                       
                        mapper = new ObjectMapper();          
                        String jwtjson =  httph.executePost(mapper.writeValueAsString(map), "http://"+ip+"/login.cgi");
                        
                        // finally get the value of the jwt
                        Map<String, String> jwtmap = mapper.readValue(jwtjson, new TypeReference<Map<String,Object>>(){});
                        jwt = jwtmap.get("jwt");
     
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
     }
     
     public void testFunction() throws IOException{
         
         
     }
     
         // addButtons
    public void addCallsToPane() {

        flowPaneCalls.getChildren().clear();

        ConfigX cx = ConfigX.getInstance();
        ArrayList<CallContainer> acc = cx.getCalls();
        Iterator<CallContainer> iter = acc.iterator();
        while (iter.hasNext()) {
            final CallContainer cc = iter.next();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    Button x = new Button(cc.getCaption());
                    x.getStyleClass().add("button_groupcall");

                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            new OsFunctions().quickCall(cc.getDmrid(), cc.getType());
                            if (cc.getDmrid() == 4000) {
                                hbox_tgs.getChildren().clear();
                                rbmTG.setAutostaticTG(null);

                            }
                        }
                    });

                    flowPaneCalls.getChildren().add(x);

                }
            });

        }

    }
     
}
