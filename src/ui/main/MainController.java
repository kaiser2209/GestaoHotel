/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.entidades.Funcao;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class MainController implements Initializable {

    @FXML
    private MenuItem mnuCadHospede;
    @FXML
    private MenuItem mnuCadUsuario;
    @FXML
    private MenuItem mnuCadFuncao;
    @FXML
    private MenuItem mnuCheckIn;
    @FXML
    private MenuItem mnuCheckOut;
    @FXML
    private MenuItem mnuReserva;
    @FXML
    private MenuItem mnuCadApCategoria;
    @FXML
    private MenuItem mnuCadApApartamento;
    @FXML
    private Menu mnuCadApartamento;
    @FXML
    private MenuItem mnuReservaListar;
    @FXML
    private MenuItem mnuListarQuartos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        definirAcesso();
    }    

    @FXML
    private void cadastroCliente(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/hospede/cadastro/CadastroHospede.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Hóspede");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void cadastroUsuario(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/usuario/cadastro/CadastroUsuario.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Usuário");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    private void criarFuncoes() {
        
    }

    @FXML
    private void cadastroFuncao(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/funcao/cadastro/CadastroFuncao.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Função");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void cadastroCategoriaApartamento(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/categoria_ap/cadastro/CategoriaApartamentos.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Categorias para Apartamento");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void cadastroApartamento(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/apartamento/cadastro/CadastroApartamentos.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Apartamento");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void registroReserva(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/hospedagem/reserva/TelaReserva.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Apartamento");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void abrirCheckin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/hospedagem/checkin/TelaCheckIn.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Check-In");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void abrirListaHospedagens(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/hospedagem/checkout/ListaHospedagem.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Lista de Hospedagens");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    private void definirAcesso() {
        short nivelAcesso = Main.getNivelAcesso();
        mnuCadHospede.setVisible(nivelAcesso <= 5);
        mnuCadUsuario.setVisible(nivelAcesso <= 1);
        mnuCadFuncao.setVisible(nivelAcesso <= 1);
        mnuCheckIn.setVisible(nivelAcesso <= 5);
        mnuCheckOut.setVisible(nivelAcesso <= 5);
        mnuReserva.setVisible(nivelAcesso <= 5);
        mnuCadApartamento.setVisible(nivelAcesso <= 3);
        mnuReservaListar.setVisible(nivelAcesso <= 5);
    }

    @FXML
    private void listarReserva(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/hospedagem/reserva/ListaReserva.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Lista de Reservas");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void listarQuartos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/hospedagem/listarQuartos/ListarQuartos.fxml"));
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Lista de Quartos");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
