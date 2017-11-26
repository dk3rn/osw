package de.dk3rn.osw;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ArrayList;
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

    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       ConfigX xx =ConfigX.getInstance();

       tf_dmrid.setText(xx.getDmrid());
       tf_pw.setText(xx.getPassword());
       tf_ip.setText(xx.getIp());
       
       
        ObservableList items = FXCollections.observableArrayList(ConfigX.getInstance().getCalls());

        // lv_tgs.setItems(items);
        lv_tgs.setItems(items);

    }

    
    
    
    
    
    
    
    
    
    
    
    
    
//        ObservableList<String> ol = FXCollections.observableArrayList("Group Call", "Private Call");
//        cb_calltype.setItems(ol);
    @FXML
    private void removeTGFromList(ActionEvent event) {
        Object selectedItem = lv_tgs.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        lv_tgs.getItems().remove(selectedItem);

        ArrayList calls = ConfigX.getInstance().getCalls();
        //copy result to calls -- das ist ein recht wilder aufrunf... mal gucken
        ConfigX.getInstance().setCalls(new Tools().ObservableListToArrayList(lv_tgs.getItems()));
        ConfigX.getInstance().addCallsToPane();
    }

    @FXML
    private void btnWriteConfig(ActionEvent event) {
        ConfigX x = ConfigX.getInstance();
        x.setPassword(tf_pw.getText());
        x.setIp(tf_ip.getText());
        x.setDmrid(tf_dmrid.getText());
        
        x.writeConConfig();
  
    }

}
