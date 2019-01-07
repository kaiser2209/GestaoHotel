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
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.apartamento.ApartamentoBO;
import model.entidades.Apartamento;
import model.entidades.Hospede;
import model.entidades.Reserva;
import model.hospede.HospedeBO;
import model.reserva.ReservaBO;
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
    private ReservaBO rBO;
    public static LocalDate dataEntrada;
    public static LocalDate dataSaida;
    
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
    private RequiredFieldValidator campoObrigatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarValidacao();
        definirValidacao();
        hBO = new HospedeBO();
        aBO = new ApartamentoBO();
        rBO = new ReservaBO();
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
        } catch (SQLException e) {
            Mensagem.mensagemDeErroBD();
        } catch (IOException e1) {
            Mensagem.mensagemDeErro("Falha ao abrir a tela de seleção do Hóspede!\nEntre em contato"
                    + " com o administrador do Sistema!");
        }
    }

    @FXML
    private void buscarQuarto(ActionEvent event) throws IOException {
        if (dtDataEntrada.getValue() != null && dtPrevisaoSaida != null) {
            dataEntrada = dtDataEntrada.getValue();
            dataSaida = dtPrevisaoSaida.getValue();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospedagem/reserva/BuscaQuarto.fxml"));

            Parent root = loader.load();
            Scene cena = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Seleção de Quarto");
            stage.setScene(cena);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            BuscaQuartoController controller = (BuscaQuartoController) loader.getController();
            apartamento = controller.get();
            mostrarQuartoSelecionado();
        } else {
            Mensagem.mensagemDeErro("Antes de prosseguir defina a Data de Entrada "
                    + "e a Previsão de Saída");
        }
    }

    private void abrirBuscaHospede() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospedagem/checkin/BuscaHospede.fxml"));
        Parent root = loader.load();
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Seleção de Hóspede");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        BuscaHospedeController controller = (BuscaHospedeController) loader.getController();
        stage.showAndWait();
        hospede = controller.get();
        mostrarHospedeSelecionado();
    }

    private void mostrarQuartoSelecionado() {
        if (apartamento != null) {
            txtQuarto.setText(apartamento.getNumero());
        } else {
            txtQuarto.clear();
        }
    }
    
    private void mostrarHospedeSelecionado() {
        if (hospede != null) {
            txtCpf.setText(hospede.getCpf());
            txtHospede.setText(hospede.getNome());
        } else {
            txtCpf.clear();
            txtQuarto.clear();
        }
    }

    @FXML
    private void verificarDigitacao(KeyEvent event) {
        if (hospede != null) {
            txtCpf.clear();
            txtHospede.clear();
            hospede = null;
        }
    }

    @FXML
    private void salvarReserva(ActionEvent event) {
        if (validate()) {
            Reserva r = new Reserva();
            r.setHospede(hospede);
            if (!chkIgnorarQuarto.isSelected()) {
                r.setApartamento(apartamento);
            }
            r.setDataEntrada(dataEntrada);
            r.setDataSaida(dataSaida);
            r.setObservacoes(txtObservacoes.getText());
            r.setDataReserva(LocalDateTime.now());

            try {
                rBO.salvar(r);
            } catch (SQLException ex) {
                Mensagem.mensagemDeErro(ex);
            }
        } else {
            Mensagem.mensagemCamposInvalidos();
        }
    }

    @FXML
    private void cancelarReserva(ActionEvent event) {
    }
    
    private void inicializarValidacao() {
        campoObrigatorio = new RequiredFieldValidator();
        campoObrigatorio.setMessage("Obrigatório");
    }
    
    private void definirValidacao() {
        txtCpf.setValidators(campoObrigatorio);
        dtDataEntrada.setValidators(campoObrigatorio);
                
    }
    
    private boolean validate() {
        txtCpf.validate();
        dtDataEntrada.validate();
                
        return txtCpf.validate() && dtDataEntrada.validate();
    }
    
    
}
