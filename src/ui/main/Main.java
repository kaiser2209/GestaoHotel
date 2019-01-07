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
import model.entidades.Usuario;

/**
 *
 * @author guard
 */
public class Main extends Application {
    private static Usuario usuarioLogado;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale.setDefault(new Locale("pt", "BR"));
        abrirLogin();
        if (usuarioLogado != null) {
            abrirTelaPrincipal(primaryStage);
        }
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void abrirLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/login/TelaLogin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Acesso ao Sistema");
        stage.setResizable(false);
        Scene cena = new Scene(root);
        stage.setScene(cena);
        stage.showAndWait();
    }
    
    private void abrirTelaPrincipal(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage.setTitle("Gestão de Hotel");
        stage.setMaximized(true);
        Scene cena = new Scene(root);
        stage.setScene(cena);
        stage.show();
    }
    
    public static void setUsuario(Usuario u) {
        usuarioLogado = u;
    }
    
    public static short getNivelAcesso() {
        return usuarioLogado.getFuncao().getNivelAcesso();
    }
}
