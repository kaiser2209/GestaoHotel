/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospede.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class BuscaHospedeController implements Initializable {
    public static final int BUSCA_OK = 1, BUSCA_CANCELADA = 0;
    private int estadoBusca = BUSCA_CANCELADA;
    private String campo, textoBusca;

    @FXML
    private JFXComboBox<String> cboCampo;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cboCampo.getItems().addAll("CPF", "Nome");
    }    

    @FXML
    private void fazerBusca(ActionEvent event) {
        estadoBusca = BUSCA_OK;
        campo = cboCampo.getValue();
        textoBusca = txtValor.getText();
        close((Button) event.getSource());
    }

    @FXML
    private void cancelarBusca(ActionEvent event) {
        close((Button) event.getSource());
    }
    
    private void close(Button b) {
        Stage stage = (Stage) b.getScene().getWindow();
        stage.close();
    }
    
    public int get() {
        return estadoBusca;
    }
    
    public String getCampo() {
        return campo;
    }
    
    public String getTextoBusca() {
        return textoBusca;
    }
}
