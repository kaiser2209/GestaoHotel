/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.checkin;

import ui.hospedagem.reserva.*;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import model.entidades.Hospede;
import model.hospede.HospedeBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class BuscaHospedeController implements Initializable {
    private ArrayList<Hospede> hospedes;
    private ArrayList<Hospede> filtroHospedes;
    private ObservableList<Hospede> dadosTabela;
    private HospedeBO hBO;
    private Hospede hospedeSelecionado;
    
    @FXML
    private JFXTextField txtHospede;
    @FXML
    private TableView<Hospede> tabelaHospede;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hBO = new HospedeBO();
        try {
            hospedes = hBO.listar();
            filtroHospedes = filtrarDados(txtHospede.getText());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
        
        definirEventos();
        configurarTabela();
        carregarDados();
    }   
    
    private ArrayList<Hospede> filtrarDados(String nome) {
        ArrayList<Hospede> lista = new ArrayList<>();
        for(Hospede a : hospedes) {
            if (a.getNome().indexOf(nome) == 0 || nome.equals("")) {
                lista.add(a);
            }
        }
        
        return lista;
    }
    
    private void configurarTabela() {

        //Configurando as colunas da tabela
        TableColumn<Hospede, String> colNome = new TableColumn("Nome");
        TableColumn<Hospede, String> colCpf = new TableColumn("CPF");
        TableColumn<Hospede, String> colCelular = new TableColumn("Celular");

        //Configurar como os valores serão lidos (nome dos atributos)
        colNome.setCellValueFactory(new PropertyValueFactory<Hospede, String>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<Hospede, String>("cpf"));
        colCelular.setCellValueFactory(new PropertyValueFactory<Hospede, String>("celularFormatado"));

        //Configurando a largura das colunas da tabela
        tabelaHospede.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNome.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 20% width
        colCpf.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 20% width
        colCelular.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabelaHospede.getColumns().addAll(colNome, colCpf, colCelular);

    }
    
    private void definirEventos() {
        txtHospede.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filtroHospedes = filtrarDados(txtHospede.getText());
                
                carregarDados();
            }
            
        });
    }
    
    private void carregarDados() {
        dadosTabela = FXCollections.observableArrayList(filtroHospedes);
        tabelaHospede.setItems(dadosTabela);
    }

    @FXML
    private void aplicarFiltro(InputMethodEvent event) {
    }

    @FXML
    private void buscaOk(ActionEvent event) {
        hospedeSelecionado = tabelaHospede.getSelectionModel().getSelectedItem();
        close((Button) event.getSource());
    }

    @FXML
    private void buscaCancelada(ActionEvent event) {
        hospedeSelecionado = null;
        close((Button) event.getSource());
    }
    
    public Hospede get() {
        return hospedeSelecionado;
    }
    
    private void close(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
