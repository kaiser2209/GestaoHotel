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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
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
    
}
