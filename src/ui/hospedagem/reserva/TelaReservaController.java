/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.reserva;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.apartamento.ApartamentoBO;
import model.entidades.Apartamento;
import model.entidades.Hospede;
import model.hospede.HospedeBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class TelaReservaController implements Initializable {
    private Hospede hospede;
    private Apartamento apartamento;
    private HospedeBO hBO;
    private ApartamentoBO aBO;
    
    @FXML
    private JFXTextField txtCpf;
    @FXML
    private JFXDatePicker dtDataEntrada;
    @FXML
    private JFXDatePicker dtPrevisaoSaida;
    @FXML
    private JFXTextField txtQuarto;
    @FXML
    private JFXCheckBox chkIgnorarQuarto;
    @FXML
    private JFXTextArea txtObservacoes;
    @FXML
    private JFXTextField txtHospede;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hBO = new HospedeBO();
        aBO = new ApartamentoBO();
    }    

    @FXML
    private void buscarHospede(ActionEvent event) {
        String cpf = txtCpf.getText();
        try {
            hospede = hBO.buscarPeloCpf(cpf);
            if (hospede == null) {
                abrirBuscaHospede();
            } else {
                txtHospede.setText(hospede.getNome());
            }
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
    }

    @FXML
    private void buscarQuarto(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospedagem/reserva/BuscaQuarto.fxml"));
        Parent root = loader.load();
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Selção de Quarto");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        BuscaQuartoController controller = (BuscaQuartoController) loader.getController();
        stage.showAndWait();
    }

    private void abrirBuscaHospede() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirBuscaApartamento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
