package de.dk3rn.osw;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Nuke
 */
public class SettingsController implements Initializable {

    @FXML
    private AnchorPane anchorPane2;
    @FXML
    private ListView<?> lv_tgs;
    @FXML
    private Button btn_remove;
    @FXML
    private Button btn_add;
    @FXML
    private ChoiceBox cb_calltype;
    @FXML
    private TextField tf_dmrid;
    @FXML
    private TextField tf_pw;
    @FXML
    private TextField tf_ip;
    @FXML
    private Button btn_writeConfig;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_tgdmrid;

    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        
       // Setting ConConfig 
       ConfigX xx =ConfigX.getInstance();
       tf_dmrid.setText(xx.getDmrid());
       tf_pw.setText(xx.getPassword());
       tf_ip.setText(xx.getIp());
  
        // Playing with Calls / TGs
        ArrayList al = xx.getCalls();
        ArrayList hbl = new Tools().copyToHBoxList(al);
        ObservableList myObservableList = FXCollections.observableList(hbl);
        lv_tgs.setItems(myObservableList);
        
        // ChoiceBox
        cb_calltype.setItems(FXCollections.observableArrayList("Group Call", "Private Call"));
        cb_calltype.getSelectionModel().select(0);



       

       
   

    }

    
    


    @FXML
    private void removeTGFromList(ActionEvent event) {
        Object selectedItem = lv_tgs.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        lv_tgs.getItems().remove(selectedItem);
        // So far it works
       
        ArrayList calls = ConfigX.getInstance().getCalls();
        



        //copy result to calls -- das ist ein recht wilder aufrunf... mal gucken
        ArrayList al = new Tools().copyHBoxListToList(lv_tgs.getItems());
        ConfigX.getInstance().setCalls(al);
        
        
        
        
        
        
        ConfigX.getInstance().addCallsToPane();
    }

    @FXML
    private void btnWriteConfig(ActionEvent event) {
        ConfigX x = ConfigX.getInstance();
        x.setPassword(tf_pw.getText());
        x.setIp(tf_ip.getText());
        x.setDmrid(tf_dmrid.getText());
        
        x.writeConConfig();
        x.genToken();
        x.writeCallConfig(x.getCalls());
        x.initialize();
  
    }

    @FXML
    private void addTGs(ActionEvent event) {
        ObservableList ol = lv_tgs.getItems();
        int dmrid = Integer.parseInt(tf_tgdmrid.getText());
        int type = 0;
        
        String selectedItem = (String) cb_calltype.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        
        if (selectedItem.equals("Private Call"))
        {
            type = 1; 
        }
        else
        {
            type = 0;
        }
        
        HBoxCell hbc = new HBoxCell(tf_name.getText(), dmrid, type); 
        ol.add(hbc);
       
        ConfigX cX = ConfigX.getInstance();
        cX.setCalls(new Tools().copyHBoxListToList(ol));
        cX.writeCallConfig(cX.getCalls());
        cX.addCallsToPane();
        
        
        
        
        
    }

}
