/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.senha.cadastro;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.entidades.Usuario;
import model.usuario.UsuarioBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class CadastroSenhaController implements Initializable {
    private UsuarioBO uBO;

    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtCpf;
    @FXML
    private JFXPasswordField txtSenha;
    @FXML
    private JFXPasswordField txtConfirmaSenha;
    private RequiredFieldValidator campoObrigatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        uBO = new UsuarioBO();
        inicializarValidacao();
        definirValidacao();
    }    

    @FXML
    private void salvaSenha(ActionEvent event) {
        if (validate()) {
            try {
                Usuario u = uBO.buscarPeloCpf(txtCpf.getText());
                if (u != null) {
                    String[] nome = u.getNome().split(" ");
                    if (nome[0].toUpperCase().equals(txtNome.getText().toUpperCase())) {
                        if (txtSenha.getText().equals(txtConfirmaSenha.getText())) {
                            uBO.salvarSenha(u, txtSenha.getText());
                            Mensagem.mensagemDeErro("Senha cadastrada com sucesso!\n"
                                    + "Você pode prosseguir com o login.");
                            close((Button) event.getSource());
                        } else {
                            Mensagem.mensagemDeErro("A confirmação de senha não "
                                    + "confere!");
                        }
                    } else {
                        Mensagem.mensagemDeErro("Dados inválidos!\nConfira se os "
                                + "dados inseridos estão certos e tente novamente!");
                    }
                } else {
                    Mensagem.mensagemDeErro("Seus dados não foram encontrados no sistema.\n"
                            + "Peça ao administrador para fazer o seu cadastro e tente novamente!");
                }
            } catch (SQLException ex) {
                Mensagem.mensagemDeErro(ex);
            }
        }
    }
    
    private void inicializarValidacao() {
        campoObrigatorio = new RequiredFieldValidator();
    }
    
    private void definirValidacao() {
        txtNome.setValidators(campoObrigatorio);
        txtCpf.setValidators(campoObrigatorio);
        txtSenha.setValidators(campoObrigatorio);
        txtConfirmaSenha.setValidators(campoObrigatorio);
    }
    
    private boolean validate() {
        txtNome.validate();
        txtCpf.validate();
        txtSenha.validate();
        txtConfirmaSenha.validate();
        
        return txtNome.validate() && txtCpf.validate() && txtSenha.validate() &&
                txtConfirmaSenha.validate();
    }
    private void close(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
