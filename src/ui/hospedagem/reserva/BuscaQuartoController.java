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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ArrayList<Apartamento> apartamentos;
    private ArrayList<Apartamento> filtroApartamentos;
    private ObservableList<Apartamento> dadosTabela;
    private ApartamentoBO aBO;
    @FXML
    private JFXTextField txtQuarto;
    @FXML
    private TableView<Apartamento> tabelaQuartos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aBO = new ApartamentoBO();
        try {
            apartamentos = aBO.listar();
            filtroApartamentos = filtrarDados(txtQuarto.getText());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
        
        configurarTabela();
        carregarDados();
    }    

    @FXML
    private void buscaOk(ActionEvent event) {
    }

    @FXML
    private void buscaCancelada(ActionEvent event) {
    }

    @FXML
    private void aplicarFiltro(InputMethodEvent event) {
        filtroApartamentos = filtrarDados(txtQuarto.getText());
        Mensagem.mensagemDeErro(filtroApartamentos.size() + "");
        tabelaQuartos.refresh();
    }

    private ArrayList<Apartamento> filtrarDados(String quarto) {
        ArrayList<Apartamento> lista = new ArrayList<>();
        for(Apartamento a : apartamentos) {
            if (a.getNumero().indexOf(quarto) > 0 || quarto.equals("")) {
                lista.add(a);
            }
        }
        
        return lista;
    }
    
    private void configurarTabela() {

        //Configurando as colunas da tabela
        TableColumn<Apartamento, String> colNumero = new TableColumn("Quarto");
        TableColumn<Apartamento, String> colCategoria = new TableColumn("Categoria");
        TableColumn<Apartamento, Float> colValor = new TableColumn("Diária");

        //Configurar como os valores serão lidos (nome dos atributos)
        colNumero.setCellValueFactory(new PropertyValueFactory<Apartamento, String>("numero"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Apartamento, String>("nomeCategoria"));
        colValor.setCellValueFactory(new PropertyValueFactory<Apartamento, Float>("diaria"));

        //Configurando a largura das colunas da tabela
        tabelaQuartos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNumero.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colCategoria.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colValor.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabelaQuartos.getColumns().addAll(colNumero, colCategoria, colValor);

    }

    private void carregarDados() {
        dadosTabela = FXCollections.observableArrayList(filtroApartamentos);
        tabelaQuartos.setItems(dadosTabela);
    }
}
