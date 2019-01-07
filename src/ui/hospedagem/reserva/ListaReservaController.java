/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.reserva;

import ui.hospedagem.checkout.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.entidades.Hospedagem;
import model.entidades.Reserva;
import model.hospedagem.HospedagemDAO;
import model.reserva.ReservaBO;
import model.reserva.ReservaDAO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class ListaReservaController implements Initializable {
    private ReservaBO rBO;
    private ArrayList<Reserva> reservas;
    private ArrayList<Reserva> reservasVisiveis;
    private ObservableList<Reserva> dadosTabela;
    private static Reserva reservaSelecionada;
    
    @FXML
    private JFXComboBox<String> cboPesquisarPor;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private TableView<Reserva> tblReserva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rBO = new ReservaBO();
        cboPesquisarPor.getItems().addAll("Hóspede", "Quarto");
        cboPesquisarPor.getSelectionModel().select(0);
        definirEventos();
        configurarTabela();
        try {
            reservas = rBO.listarReservasAtivas();
            reservasVisiveis = filtrarDados(cboPesquisarPor.getValue(), txtValor.getText());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErro("Houve um erro ao recuperar a lista de "
                    + "hospedagens. Contacte o administrador do sistema!");
        }
        carregarDados();
    }    

    @FXML
    private void cancelarReserva(ActionEvent event) throws IOException {
        Reserva r = tblReserva.getSelectionModel().getSelectedItem();
        if (r != null) {
            Optional<ButtonType> op = Mensagem.mensagemDeConfirmacao("Deseja "
                    + "cancelar a reserva selecionada?", "Cancelar Reserva");
            
            if (op.get() == ButtonType.OK) {
                reservaSelecionada = r;

                try {
                    rBO.cancelarReserva(r);
                } catch (SQLException ex) {
                    Mensagem.mensagemDeErro(ex);
                }
            }
            
        } else {
            Mensagem.mensagemDeErro("Falha ao realizar o Check Out! Primeiro escolha um "
                    + "item da lista antes de prosseguir!");
        }
        
        try {
            reservas = rBO.listarReservasAtivas();
            reservasVisiveis = filtrarDados(cboPesquisarPor.getValue(), txtValor.getText());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErro(ex);
        }
        
        carregarDados();
        
    }

    @FXML
    private void sair(ActionEvent event) {
        close((Button) event.getSource());
    }
    
    private void carregarDados() {
        dadosTabela = FXCollections.observableArrayList(reservasVisiveis);
        tblReserva.setItems(dadosTabela);
    }
    
    private ArrayList<Reserva> filtrarDados(String campo, String valor) {
        ArrayList<Reserva> lista = new ArrayList<>();
        if (campo.equals("Hóspede")) {
            for(Reserva r : reservas) {
                if (r.getHospede().getNome().indexOf(valor) == 0 || valor.equals("")) {
                    lista.add(r);
                }
            }    
        } else if (campo.equals("Quarto")) {
            for(Reserva r : reservas) {
                if (r.getApartamento().getNumero().indexOf(valor) == 0 || valor.equals("")) {
                    lista.add(r);
                }
            }
        }
        
        
        return lista;
    }
    
    private void configurarTabela() {

        //Configurando as colunas da tabela
        TableColumn<Reserva, String> colQuarto = new TableColumn("Quarto");
        TableColumn<Reserva, String> colHospede = new TableColumn("Hóspede");
        TableColumn<Reserva, String> colEntrada = new TableColumn("Data de Entrada");

        //Configurar como os valores serão lidos (nome dos atributos)
        colQuarto.setCellValueFactory(new PropertyValueFactory<Reserva, String>("numeroApartamento"));
        colHospede.setCellValueFactory(new PropertyValueFactory<Reserva, String>("nomeHospede"));
        colEntrada.setCellValueFactory(new PropertyValueFactory<Reserva, String>("dataEntradaFormatada"));

        //Configurando a largura das colunas da tabela
        tblReserva.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colQuarto.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colHospede.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 20% width
        colEntrada.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tblReserva.getColumns().addAll(colQuarto, colHospede, colEntrada);

    }
    
    private void definirEventos() {
        txtValor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                reservasVisiveis = filtrarDados(cboPesquisarPor.getValue(), txtValor.getText());
                
                carregarDados();
            }
            
        });
        
        cboPesquisarPor.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                txtValor.clear();
            }
            
        });
    }
    
    private void close(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    
    public static Reserva getHospedagemSelecionada() {
        return reservaSelecionada;
    }
}
