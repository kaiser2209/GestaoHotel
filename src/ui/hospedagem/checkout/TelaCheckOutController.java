/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.checkout;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.entidades.Hospedagem;
import model.entidades.PagamentoHospedagem;
import model.hospedagem.HospedagemBO;
import model.pagamentoHospedagem.PagamentoHospedagemBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class TelaCheckOutController implements Initializable {
    private Hospedagem hospedagem;
    private HospedagemBO hBO;
    private PagamentoHospedagemBO pBO;
    @FXML
    private Label quarto;
    @FXML
    private JFXTextField txtHospede;
    @FXML
    private JFXTextField txtCelular;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private Label valorTotal;
    @FXML
    private JFXTextField txtNumeroDiarias;
    @FXML
    private JFXTextField txtValorDiaria;
    @FXML
    private JFXTextField txtTotalDiaria;
    @FXML
    private JFXTextField txtPercentual;
    @FXML
    private JFXTextField txtTotalDesconto;
    @FXML
    private JFXTextField txtAcrescimo;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hBO = new HospedagemBO();
        pBO = new PagamentoHospedagemBO();
        hospedagem = ListaHospedagemController.getHospedagemSelecionada();
        carregarDados();
    } 
    
    public void carregarDados() {
        quarto.setText(hospedagem.getApartamento().getNumero());
        txtHospede.setText(hospedagem.getHospedes().get(0).getNome());
        txtCelular.setText(hospedagem.getHospedes().get(0).getCelular());
        txtEmail.setText(hospedagem.getHospedes().get(0).getEmail());
        txtNumeroDiarias.setText(String.valueOf(getDiarias()));
        txtValorDiaria.setText(hospedagem.getDiariaFormatada());
        txtPercentual.setText("0");
        DecimalFormat df = new DecimalFormat("R$ #0.00");
        txtTotalDiaria.setText(df.format(getTotalDiarias()));
        atualizarValores();
        definirEventos();
        
    }
    
    public int getDiarias() {
        return (int) ChronoUnit.DAYS.between(hospedagem.getDataEntrada(), LocalDate.now());
    }

    @FXML
    private void salvarCheckOut(ActionEvent event) {
        hospedagem.setDataSaida(LocalDate.now());
        PagamentoHospedagem pagamento = new PagamentoHospedagem();
        pagamento.setHospedagem(hospedagem);
        pagamento.setTotalAcrescimo(getAcrescimo());
        pagamento.setTotalValorDiaria(getTotalDiarias());
        pagamento.setTotalDesconto(getDesconto());
        
        
        try {
            hBO.checkout(hospedagem);
            pBO.salvar(pagamento);
        } catch (SQLException ex) {
            Mensagem.mensagemDeErro("Houve um erro ao realizar o Check Out");
        }
        
        close((Button) event.getSource());
    }

    @FXML
    private void cancelarCheckOut(ActionEvent event) {
        close((Button) event.getSource());
    }
    
    private float getTotalDiarias() {
        return getDiarias() * hospedagem.getDiaria();
    }
   
    private float getDesconto() {
        NumberFormat nf = NumberFormat.getInstance();
        String valor = txtTotalDesconto.getText().replace("R$ ", "");
        try {
            return nf.parse(valor).floatValue();
        } catch (ParseException ex) {
            Mensagem.mensagemDeErro("Houve um erro ao recupear o valor de desconto!");
        }
        
        return 0;
    }
    
    private float getAcrescimo(){
        if (!(txtAcrescimo.getText().equals("") || txtAcrescimo.getText().equals(null))) {
            NumberFormat nf = NumberFormat.getInstance();
            try {
                return nf.parse(txtAcrescimo.getText()).floatValue();
            } catch (ParseException ex) {
                Mensagem.mensagemDeErro("O valor informado de acréscimo não é "
                        + "válido!");
            }
        }
        
        return 0;
    }
    
    private void calcularDesconto() throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        float percentual = 0;
        if (!(txtPercentual.getText().equals("") || txtPercentual.getText().equals(null))) {
            percentual = nf.parse(txtPercentual.getText()).floatValue() / 100;
        }
        DecimalFormat df = new DecimalFormat("R$ #0.00");
        txtTotalDesconto.setText(df.format(getTotalDiarias() * percentual));
    }
    
    private float getValorTotal() {
        return getTotalDiarias() - getDesconto() + getAcrescimo();
    }
    
    private void definirEventos() {
        txtPercentual.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                atualizarValores();
            }
            
        });
        
        txtAcrescimo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                atualizarValores();
            }
            
        });
    }
    
    private void atualizarValores() {
        try {
            calcularDesconto();
        } catch (ParseException ex) {
            Mensagem.mensagemDeErro("Houve um erro ao calcular o desconto!");
        }
        DecimalFormat df = new DecimalFormat("R$ #0.00");
        valorTotal.setText(df.format(getValorTotal()));
    }
    
    private void close(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
