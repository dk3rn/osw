/* CONTROLLER (MVC) */
package de.dk3rn.osw;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class FXMLController implements Initializable {

    @FXML
    public HBox hbox_tgs;

    @FXML
    private FlowPane flowPaneCalls;

    ReadBrandmeisterTGs rbmTG;
    
    @FXML
    private HBox hbox_top;

    @FXML
    private TextField textCallDMRID;

    int calltype;
    @FXML
    private Button xxxButton;
    @FXML
    private Button btnCalltypeGC;
    @FXML
    private Button btnCalltypePC;
    @FXML
    private Button btn_settings;
    @FXML
    private Label lbl_reRoute;
    


    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        //System.out.println(new OsFunctions().reRouting(3100));
        //System.out.println(new OsFunctions().quickCall(4000)); 
        //String X = new OsFunctions().status();
        //System.out.println(X);
        //System.out.println(new Tools().extractSubStringFromJSON(X, "connected_to"));

        int dmrid = Integer.valueOf(textCallDMRID.getText());
        new OsFunctions().quickCall(dmrid, calltype);

    }

    @Override // it is just an overwritten method
    public void initialize(URL url, ResourceBundle rb) {

        rbmTG = new ReadBrandmeisterTGs();
        ConfigX.getInstance().setRbmTG(rbmTG);
        rbmTG.refreshTGDisplay(hbox_tgs);

        
        ConfigX.getInstance().setHbox_tgs(hbox_tgs);
        ConfigX.getInstance().setFlowPaneCalls(flowPaneCalls);
        ConfigX.getInstance().initialize();
        ConfigX.getInstance().addCallsToPane();
        ConfigX.getInstance().setLbl_reRoute(lbl_reRoute);
        
        
        // set group call active
        initActiveGroupCall();


    }

    @FXML
    private void flowpaneButtons(MouseEvent event) {
    }




    @FXML
    private void btnCalltypeGC(ActionEvent event) {
//        btnCalltypeGC.getStyleClass().add("activeButton");
//        btnCalltypePC.getStyleClass().add("inactiveButton");

        btnCalltypeGC.getStyleClass().clear();
        btnCalltypeGC.getStyleClass().add("button-other-active");
        btnCalltypePC.getStyleClass().clear();
        btnCalltypePC.getStyleClass().add("button-other");
        
        calltype = 0; //groupcall
    }

    @FXML
    private void btnCalltypePC(ActionEvent event) {
//        btnCalltypePC.getStyleClass().add("activeButton");
//        btnCalltypeGC.getStyleClass().add("inactiveButton");
        
        btnCalltypePC.getStyleClass().clear();
        btnCalltypePC.getStyleClass().add("button-other-active");
        btnCalltypeGC.getStyleClass().clear();
        btnCalltypeGC.getStyleClass().add("button-other");
        
        
        calltype = 1; // privateCall
    }
    
    private void initActiveGroupCall() {
        btnCalltypeGC.getStyleClass().clear();
        btnCalltypeGC.getStyleClass().add("button-other-active");
        btnCalltypePC.getStyleClass().clear();
        btnCalltypePC.getStyleClass().add("button-other");

        calltype = 0; //groupcall
    }


    @FXML
    private void changeSettings(ActionEvent event) {
        ConfigX.getInstance().getRoot1().show();

        
    }

}

