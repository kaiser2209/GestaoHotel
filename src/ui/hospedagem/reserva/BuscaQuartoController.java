/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.reserva;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.InputMethodEvent;
import model.apartamento.ApartamentoBO;
import model.entidades.Apartamento;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class BuscaQuartoController implements Initializable {
    ArrayList<Apartamento> apartamentos;
    ArrayList<Apartamento> filtroApartamentos;
    @FXML
    private JFXTextField txtQuarto;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ApartamentoBO aBO = new ApartamentoBO();
        try {
            apartamentos = aBO.listar();
            filtroApartamentos = filtrarDados(txtQuarto.getText());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
    }    

    @FXML
    private void buscaOk(ActionEvent event) {
    }

    @FXML
    private void buscaCancelada(ActionEvent event) {
    }

    @FXML
    private void aplicarFiltro(InputMethodEvent event) {
    }

    private ArrayList<Apartamento> filtrarDados(String quarto) {
        ArrayList<Apartamento> lista = new ArrayList<>();
        for(Apartamento a : apartamentos) {
            if (a.getNumero().indexOf(quarto) > 0) {
                lista.add(a);
            }
        }
        
        return lista;
    }
    
    
}
