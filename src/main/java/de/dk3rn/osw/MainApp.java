package de.dk3rn.osw;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //stage.initStyle(StageStyle.UTILITY);
        stage.initStyle(StageStyle.UNIFIED);
        //stage.initStyle(StageStyle.UNIFIED);
                
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));   
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/dark-theme.css");    

        stage.setTitle("OSW DK3RN/DO8OD");
        stage.setScene(scene);
        stage.show();
        
        
        

        // create Stage
        Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
        Scene scene2 = new Scene(root1);
        //scene.getStylesheets().add("/styles/dark-theme.css");
        Stage stage2 = new Stage();
        stage2.setTitle("OpenSpot Widget Settings");
        stage2.setScene(scene2);
        ConfigX.getInstance().setRoot1(stage2);

        // stage2.show();
        
        
        

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // ConfigX.getInstance().initialCallConfigWrite();
        launch(args);
        System.exit(0);
    }

}
