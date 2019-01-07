/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.entidades.Usuario;
import model.usuario.UsuarioBO;
import ui.main.Main;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class TelaLoginController implements Initializable {

    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtSenha;
    private RequiredFieldValidator campoObrigatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cadastrarSenha(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/senha/cadastro/CadastroSenha.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Senha");
        stage.setResizable(false);
        Scene cena = new Scene(root);
        stage.setScene(cena);
        stage.showAndWait();
        
    }

    @FXML
    private void efetuarLogin(ActionEvent event) {
        UsuarioBO uBO = new UsuarioBO();
        try {
            Usuario u = uBO.login(txtUsuario.getText(), txtSenha.getText());
            if (u != null) {
                Main.setUsuario(u);
                close((Button) event.getSource());
            } else {
                Mensagem.mensagemDeErro("Usuário ou senha inválidos!");
            }
        } catch (SQLException ex) {
            Mensagem.mensagemDeErro(ex);
        }
    }
    
    private void inicializarValidacao() {
        campoObrigatorio = new RequiredFieldValidator();
    }
    
    private void definirValidacao() {
        txtUsuario.setValidators(campoObrigatorio);
        txtSenha.setValidators(campoObrigatorio);
        
    }
    
    private boolean validate() {
        txtUsuario.validate();
        txtSenha.validate();
        
        return txtUsuario.validate() && txtSenha.validate();
    }
    
    private void close(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
