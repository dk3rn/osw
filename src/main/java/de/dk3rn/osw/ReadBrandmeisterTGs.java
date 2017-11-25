/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk3rn.osw;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author Nuke
 */
public class ReadBrandmeisterTGs {

    HBox hbox_tgs;
    public String autostaticTG;

    public String getAutostaticTG() {
        return autostaticTG;
    }

    public void setAutostaticTG(String autostaticTG) {
        this.autostaticTG = autostaticTG;
    }

    public void refreshTGDisplay(final HBox hbox_tgs) {

        this.hbox_tgs = hbox_tgs;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                try {
                    ConfigX cx = ConfigX.getInstance();
                    String brandmeisterURL = cx.getHttph().executePost("", "https://api.brandmeister.network/v1.0/repeater/?action=PROFILE&q="+cx.getDmrid());

                    ObjectMapper mapper = new ObjectMapper();
                    Map<Object, Object> jsonMap = mapper.readValue(brandmeisterURL, new TypeReference<Map<Object, Object>>() {
                    });

                    ArrayList staticTGs = (ArrayList) jsonMap.get("staticSubscriptions");
                    ArrayList dynamicTGs = (ArrayList) jsonMap.get("dynamicSubscriptions");

                    // Testausgabe -----------------------------
                    final Iterator iterStatTGs = staticTGs.iterator();
                    final Iterator iterDynTGs = dynamicTGs.iterator();

                    
                    // Delete all TGs from hbox
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            hbox_tgs.getChildren().clear();
                        }

                    });

                    // Look for autostatic TG
                    autostaticTG = findAutostaticTG(dynamicTGs);
                    

                    while (iterStatTGs.hasNext()) {
                        Map<String, Object> mapStatTG = (Map<String, Object>) iterStatTGs.next();
                        String tg = mapStatTG.get("talkgroup").toString();
                        addTGsToHbox(tg, 0, hbox_tgs);
                    }

                    while (iterDynTGs.hasNext()) {
                        Map<String, Object> mapDynTG = (Map<String, Object>) iterDynTGs.next();
                        String tg = mapDynTG.get("talkgroup").toString();
                        if (!tg.equals(autostaticTG)) {
                            addTGsToHbox(tg, 1, hbox_tgs);
                        }

                    }

                    System.out.println("test");

                } catch (Exception ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }, 500, 10000);
    }

    // ---
    void addTGsToHbox(final String tg, final int type, final HBox hbox_tgs) {
// Controlled Access to the GUI
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                // Decision which kind of TG should be added        
                switch (type) {
                    // Add Static TG
                    case 0: {
                        Label x = new Label(tg);
                        x.getStyleClass().add("label_static_tg");
                        hbox_tgs.getChildren().add(x);
                        break;
                    }
                    // Add Dynamic TG
                    case 1: {
                        Label x = new Label(tg);
                        x.getStyleClass().add("label_dynamic_tg");
                        hbox_tgs.getChildren().add(x);
                        break;
                    }
                    // Add Auto-Static TG
                    case 2: {
                        Label x = new Label(tg);
                        x.getStyleClass().add("label_autostatic_tg");
                        hbox_tgs.getChildren().add(x);
                        break;
                    }
                }
            }
        });
    }

    /**
     *
     * @param dynamicTGs - Array containting all dynamic TGs
     * @return String with the name of the Talkgroup which is auto-static.
     */
    private String findAutostaticTG(ArrayList dynamicTGs) {

        // If there a no dynic TGs, the last autostaticTG 
        // is returned, because it is still there, even it is not shown in API
        if (dynamicTGs == null) {
            return autostaticTG;
        }

        Iterator iterDynTGs = dynamicTGs.iterator();
        Double memTime = 0.0;
        Map<String, Object> map = null;

        while (iterDynTGs.hasNext()) {
            Map<String, Object> mapDynTG = (Map<String, Object>) iterDynTGs.next();
            Double time = (Double) mapDynTG.get("timeout");
            if (time > memTime) {
                memTime = time;
                map = mapDynTG;
            }

        }

        if (map == null) {
            addTGsToHbox(autostaticTG, 2, hbox_tgs);
            return autostaticTG;
        }

        addTGsToHbox(map.get("talkgroup").toString(), 2, hbox_tgs);
        return map.get("talkgroup").toString();
    }

}
