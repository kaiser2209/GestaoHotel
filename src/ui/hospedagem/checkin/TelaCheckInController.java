/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.checkin;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
public class TelaCheckInController implements Initializable {
    private ArrayList<Apartamento> apartamentos;
    private ApartamentoBO aBO;
    private Hospede hospede;
    private Apartamento apartamento;
    
    @FXML
    private JFXTextField txtCpf;
    @FXML
    private JFXTextField txtHospede;
    @FXML
    private JFXTextField txtCelular;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtQuarto;
    @FXML
    private JFXTextField txtCategoria;
    @FXML
    private JFXTextField txtRamal;
    @FXML
    private TableView<Hospede> tabelaHospedes;
    @FXML
    private JFXTextField txtProcedencia;
    @FXML
    private JFXTextField txtDestino;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aBO = new ApartamentoBO();
        try {
            apartamentos = aBO.listar();
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
        
    }   
    
    @FXML
    private void buscarHospede(ActionEvent event) {
        String cpf = txtCpf.getText();
        try {
            HospedeBO hBO = new HospedeBO();
            hospede = hBO.buscarPeloCpf(cpf);
            if (hospede == null) {
                abrirBuscaHospede();
            } else {
                txtHospede.setText(hospede.getNome());
                txtCelular.setText(hospede.getCelular());
                txtEmail.setText(hospede.getEmail());
            }
        } catch (SQLException e) {
            Mensagem.mensagemDeErroBD();
        } catch (IOException e1) {
            Mensagem.mensagemDeErro("Falha ao abrir a tela de seleção do Hóspede!\nEntre em contato"
                    + " com o administrador do Sistema!");
        }
    }
    
    private void abrirBuscaHospede() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospedagem/checkin/BuscaHospede.fxml"));
        Parent root = loader.load();
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Seleção de Quarto");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        BuscaHospedeController controller = (BuscaHospedeController) loader.getController();
        stage.showAndWait();
        hospede = controller.get();
        mostrarHospedeSelecionado();
    }
    
    private void mostrarHospedeSelecionado() {
        if (hospede != null) {
            txtCpf.setText(hospede.getCpf());
            txtHospede.setText(hospede.getNome());
        } else {
            txtCpf.clear();
            txtHospede.clear();
            txtCelular.clear();
            txtEmail.clear();
        }
    }

    @FXML
    private void verificarDigitacao(KeyEvent event) {
    }

    @FXML
    private void buscarQuarto(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospedagem/checkin/BuscaQuarto.fxml"));

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
    }
    
    private void mostrarQuartoSelecionado() {
        if (apartamento != null) {
            txtQuarto.setText(apartamento.getNumero());
            txtCategoria.setText(apartamento.getNomeCategoria());
            txtRamal.setText(String.valueOf(apartamento.getRamal()));
        } else {
            txtQuarto.clear();
            txtCategoria.clear();
            txtRamal.clear();
        }
    }
    
    private void configurarTabela() {

        //Configurando as colunas da tabela
        TableColumn<Hospede, String> colNome = new TableColumn("Nome");
        TableColumn<Hospede, LocalDate> colNascimento = new TableColumn("Nascimento");
        TableColumn<Hospede, String> colCelular = new TableColumn("Celular");

        //Configurar como os valores serão lidos (nome dos atributos)
        colNome.setCellValueFactory(new PropertyValueFactory<Hospede, String>("nome"));
        colNascimento.setCellValueFactory(new PropertyValueFactory<Hospede, LocalDate>("dataNascimento"));
        colCelular.setCellValueFactory(new PropertyValueFactory<Hospede, String>("celular"));

        //Configurando a largura das colunas da tabela
        tabelaHospedes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNome.setMaxWidth(1f * Integer.MAX_VALUE * 15); // 20% width
        colNascimento.setMaxWidth(1f * Integer.MAX_VALUE * 60); // 20% width
        colCelular.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabelaHospedes.getColumns().addAll(colNome, colNascimento, colCelular);

    }

    @FXML
    private void adicionarHospede(ActionEvent event) {
    }

    @FXML
    private void removerHospede(ActionEvent event) {
    }

    @FXML
    private void salvarHospedagem(ActionEvent event) {
    }

    @FXML
    private void cancelarHospedagem(ActionEvent event) {
    }
    
}
