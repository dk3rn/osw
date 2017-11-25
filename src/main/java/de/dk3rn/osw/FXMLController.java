/* CONTROLLER (MVC) */
package de.dk3rn.osw;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
        rbmTG.refreshTGDisplay(hbox_tgs);

        ConfigX.getInstance().setHbox_tgs(hbox_tgs);
        ConfigX.getInstance().setFlowPaneCalls(flowPaneCalls);
        ConfigX.getInstance().initialize();
        addCallsToPane();

    }

    @FXML
    private void flowpaneButtons(MouseEvent event) {
    }

    // addButtons
    public void addCallsToPane() {

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


    @FXML
    private void btnCalltypeGC(ActionEvent event) {
//        btnCalltypeGC.getStyleClass().add("activeButton");
//        btnCalltypePC.getStyleClass().add("inactiveButton");
        
        btnCalltypeGC.setStyle("-fx-background-color: green");
        btnCalltypePC.setStyle("-fx-background-color: grey");
        
        calltype = 0; //groupcall
    }

    @FXML
    private void btnCalltypePC(ActionEvent event) {
//        btnCalltypePC.getStyleClass().add("activeButton");
//        btnCalltypeGC.getStyleClass().add("inactiveButton");
        
        btnCalltypePC.setStyle("-fx-background-color: green");
        btnCalltypeGC.setStyle("-fx-background-color: grey");
        
        
        calltype = 1; // privateCall
    }

}


