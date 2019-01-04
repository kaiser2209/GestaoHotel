/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.apartamento.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
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
public class BuscaApartamentoController implements Initializable {
    public static final int BUSCA_OK = 1, BUSCA_CANCELADA = 0;
    private int estadoBusca = BUSCA_CANCELADA;
    private String textoBusca;
    
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
    }    

    @FXML
    private void fazerBusca(ActionEvent event) {
        estadoBusca = BUSCA_OK;
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
    
    public String getTextoBusca() {
        return textoBusca;
    }
    
}
