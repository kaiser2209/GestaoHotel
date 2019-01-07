/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.checkout;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.entidades.Hospedagem;
import model.hospedagem.HospedagemDAO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class ListaHospedagemController implements Initializable {
    private HospedagemDAO hospedagemDAO;
    private ArrayList<Hospedagem> hospedagens;
    private ArrayList<Hospedagem> hospedagensVisiveis;
    private ObservableList<Hospedagem> dadosTabela;
    private static Hospedagem hospedagemSelecionada;
    
    @FXML
    private JFXComboBox<String> cboPesquisarPor;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private TableView<Hospedagem> tblHospedagem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hospedagemDAO = new HospedagemDAO();
        cboPesquisarPor.getItems().addAll("Hóspede", "Quarto");
        cboPesquisarPor.getSelectionModel().select(0);
        definirEventos();
        configurarTabela();
        try {
            hospedagens = hospedagemDAO.listar(LocalDate.now());
            hospedagensVisiveis = filtrarDados(cboPesquisarPor.getValue(), txtValor.getText());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErro("Houve um erro ao recuperar a lista de "
                    + "hospedagens. Contacte o administrador do sistema!");
        }
        carregarDados();
    }    

    @FXML
    private void confirmarCheckOut(ActionEvent event) throws IOException {
        Hospedagem h = tblHospedagem.getSelectionModel().getSelectedItem();
        if (h != null) {
            hospedagemSelecionada = h;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospedagem/checkout/TelaCheckOut.fxml"));
            Parent root = loader.load();
            Scene cena = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Check Out");
            stage.setScene(cena);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            //BuscaQuartoController controller = (BuscaQuartoController) loader.getController();
            //apartamento = controller.get();
            //mostrarQuartoSelecionado();
            //calcularDiaria();

            close((Button) event.getSource());
        } else {
            Mensagem.mensagemDeErro("Falha ao realizar o Check Out! Primeiro escolha um "
                    + "item da lista antes de prosseguir!");
        }
    }

    @FXML
    private void cancelarCheckOut(ActionEvent event) {
        close((Button) event.getSource());
    }
    
    private void carregarDados() {
        dadosTabela = FXCollections.observableArrayList(hospedagensVisiveis);
        tblHospedagem.setItems(dadosTabela);
    }
    
    private ArrayList<Hospedagem> filtrarDados(String campo, String valor) {
        ArrayList<Hospedagem> lista = new ArrayList<>();
        if (campo.equals("Hóspede")) {
            for(Hospedagem h : hospedagens) {
                if (h.getHospedes().get(0).getNome().indexOf(valor) == 0 || valor.equals("")) {
                    lista.add(h);
                }
            }    
        } else if (campo.equals("Quarto")) {
            for(Hospedagem h : hospedagens) {
                if (h.getApartamento().getNumero().indexOf(valor) == 0 || valor.equals("")) {
                    lista.add(h);
                }
            }
        }
        
        
        return lista;
    }
    
    private void configurarTabela() {

        //Configurando as colunas da tabela
        TableColumn<Hospedagem, String> colQuarto = new TableColumn("Quarto");
        TableColumn<Hospedagem, String> colHospede = new TableColumn("Hóspede");
        TableColumn<Hospedagem, String> colEntrada = new TableColumn("Data de Entrada");

        //Configurar como os valores serão lidos (nome dos atributos)
        colQuarto.setCellValueFactory(new PropertyValueFactory<Hospedagem, String>("numeroApartamento"));
        colHospede.setCellValueFactory(new PropertyValueFactory<Hospedagem, String>("nomeHospede"));
        colEntrada.setCellValueFactory(new PropertyValueFactory<Hospedagem, String>("dataEntradaFormatada"));

        //Configurando a largura das colunas da tabela
        tblHospedagem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colQuarto.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        colHospede.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 20% width
        colEntrada.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tblHospedagem.getColumns().addAll(colQuarto, colHospede, colEntrada);

    }
    
    private void definirEventos() {
        txtValor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                hospedagensVisiveis = filtrarDados(cboPesquisarPor.getValue(), txtValor.getText());
                
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
    
    public static Hospedagem getHospedagemSelecionada() {
        return hospedagemSelecionada;
    }
}
