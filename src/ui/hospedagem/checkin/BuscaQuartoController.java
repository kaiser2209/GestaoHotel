/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.checkin;

import com.jfoenix.controls.JFXDatePicker;
import ui.hospedagem.reserva.*;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import model.apartamento.ApartamentoBO;
import model.entidades.Apartamento;
import model.entidades.Hospedagem;
import model.hospedagem.HospedagemBO;
import model.reserva.ReservaBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class BuscaQuartoController implements Initializable {
    private ArrayList<Apartamento> apartamentosSemReserva;
    private ArrayList<Apartamento> apartamentosSemHospedagem;
    private ArrayList<Apartamento> apartamentos;
    private ArrayList<Apartamento> filtroApartamentos;
    private ObservableList<Apartamento> dadosTabela;
    private ApartamentoBO aBO;
    private Apartamento quartoSelecionado;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private ReservaBO rBO;
    private HospedagemBO hBO;
    
    @FXML
    private JFXTextField txtQuarto;
    @FXML
    private TableView<Apartamento> tabelaQuartos;
    @FXML
    private JFXDatePicker dtPrevisaoSaida;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aBO = new ApartamentoBO();
        rBO = new ReservaBO();
        hBO = new HospedagemBO();
        dataEntrada = LocalDate.now();
        dataSaida = LocalDate.now().plusDays(10);
        try {
            apartamentosSemReserva = rBO.buscarQuartosDisponiveis(dataEntrada, dataSaida);
            apartamentosSemHospedagem = hBO.buscarQuartosDisponiveis(dataEntrada);
            apartamentos = definirApartamentosLivres();
            filtroApartamentos = filtrarDados(txtQuarto.getText());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
            ex.printStackTrace();
        }
        
        definirEventos();
        configurarTabela();
        carregarDados();
    }    

    @FXML
    private void buscaOk(ActionEvent event) {
        quartoSelecionado = tabelaQuartos.getSelectionModel().getSelectedItem();
        close((Button) event.getSource());
    }

    @FXML
    private void buscaCancelada(ActionEvent event) {
        quartoSelecionado = null;
        close((Button) event.getSource());
    }

    @FXML
    private void aplicarFiltro(InputMethodEvent event) {
        
    }

    private ArrayList<Apartamento> filtrarDados(String quarto) {
        ArrayList<Apartamento> lista = new ArrayList<>();
        for(Apartamento a : apartamentos) {
            if (a.getNumero().indexOf(quarto) == 0 || quarto.equals("")) {
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
        colNumero.setMaxWidth(1f * Integer.MAX_VALUE * 15); // 20% width
        colCategoria.setMaxWidth(1f * Integer.MAX_VALUE * 60); // 20% width
        colValor.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabelaQuartos.getColumns().addAll(colNumero, colCategoria, colValor);

    }

    private void carregarDados() {
        dadosTabela = FXCollections.observableArrayList(filtroApartamentos);
        tabelaQuartos.setItems(dadosTabela);
    }

    private void definirEventos() {
        txtQuarto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtroApartamentos = filtrarDados(txtQuarto.getText());
                
                carregarDados();
            }
            
        });
        
        dtPrevisaoSaida.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue == null) {
                    dataSaida = LocalDate.now().plusDays(10);
                } else {
                    dataSaida = newValue;
                }
                try {
                    apartamentosSemReserva = rBO.buscarQuartosDisponiveis(dataEntrada, dataSaida);
                    apartamentos = definirApartamentosLivres();
                    filtroApartamentos = filtrarDados(txtQuarto.getText());
                    carregarDados();
                } catch (SQLException ex) {
                    Logger.getLogger(BuscaQuartoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
    
    public Apartamento get() {
        return quartoSelecionado;
    }

    private void close(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    
    private ArrayList<Apartamento> definirApartamentosLivres() {
        ArrayList<Apartamento> lista = new ArrayList<>();
        
        for (Apartamento a : apartamentosSemReserva) {
            if (apartamentosSemHospedagem.contains(a)) {
                lista.add(a);
            }
        }
        
        return lista;
    }
}
