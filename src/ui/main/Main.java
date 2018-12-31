/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import java.io.IOException;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author guard
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale.setDefault(new Locale("pt", "BR"));
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Gestão de Hotel");
        primaryStage.setMaximized(true);
        Scene cena = new Scene(root);
        primaryStage.setScene(cena);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
