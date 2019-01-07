/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.listarQuartos;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.apartamento.ApartamentoBO;
import model.entidades.Apartamento;
import model.entidades.Hospedagem;
import model.entidades.SituacaoQuarto;
import model.hospedagem.HospedagemBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class ListarQuartosController implements Initializable {

    @FXML
    private TableView<SituacaoQuarto> tblQuartos;
    private ArrayList<Apartamento> quartos;
    private ArrayList<SituacaoQuarto> situacaoQuartos;
    private ApartamentoBO aBO;
    private HospedagemBO hBO;
    private ObservableList<SituacaoQuarto> dadosTabela;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aBO = new ApartamentoBO();
        hBO = new HospedagemBO();
        try {
            quartos = aBO.listarCrescente();
            situacaoQuartos = recuperarSituacao();
            configurarTabela();
            carregarDados();
        } catch (SQLException ex) {
            Mensagem.mensagemDeErro(ex);
        }
    }    
    
    private void configurarTabela() {

        //Configurando as colunas da tabela
        TableColumn<SituacaoQuarto, String> colNumero = new TableColumn("Número");
        TableColumn<SituacaoQuarto, String> colCategoria = new TableColumn("Categoria");
        TableColumn<SituacaoQuarto, String> colSituacao = new TableColumn("Situação");

        //Configurar como os valores serão lidos (nome dos atributos)
        colNumero.setCellValueFactory(new PropertyValueFactory<SituacaoQuarto, String>("numeroApartamento"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<SituacaoQuarto, String>("nomeCategoria"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<SituacaoQuarto, String>("situacao"));

        //Configurando a largura das colunas da tabela
        tblQuartos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNumero.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colCategoria.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 20% width
        colSituacao.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tblQuartos.getColumns().addAll(colNumero, colCategoria, colSituacao);

    }
    
    private ArrayList<SituacaoQuarto> recuperarSituacao() throws SQLException {
        ArrayList<SituacaoQuarto> lista = new ArrayList<>();
        for (Apartamento a : quartos) {
            SituacaoQuarto s = new SituacaoQuarto();
            s.setApartamento(a);
            if (hBO.buscarHospedagem(a, LocalDate.now()) == null) {
                s.setSituacao("Livre");
            } else {
                s.setSituacao("Ocupado");
            }
            
            lista.add(s);
        }
        
        return lista;
    }
    
    private void carregarDados() throws SQLException {
        dadosTabela = FXCollections.observableArrayList(situacaoQuartos);
        tblQuartos.setItems(dadosTabela);
    }
    
}
