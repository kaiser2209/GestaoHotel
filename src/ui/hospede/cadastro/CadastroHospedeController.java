/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospede.cadastro;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.entidades.Hospede;
import model.hospede.HospedeBO;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class CadastroHospedeController implements Initializable {

    public final static int SEM_ACAO = 0, INCLUSAO = 1, EDICAO = 2;
    private HospedeBO hBO;
    private ArrayList<Hospede> hospedes;
    private int acao = SEM_ACAO;
    private int regAtual;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtRg;
    @FXML
    private JFXTextField txtCpf;
    @FXML
    private JFXTextField txtCep;
    @FXML
    private JFXTextField txtRua;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtBairro;
    @FXML
    private JFXTextField txtComplemento;
    @FXML
    private JFXTextField txtCidade;
    @FXML
    private JFXTextField txtPais;
    @FXML
    private JFXTextField txtTelefone;
    @FXML
    private JFXTextField txtCelular;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtEstado;
    @FXML
    private JFXDatePicker dtDataNascimento;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtNome.setText(String.valueOf(acao));
        regAtual = 0;
        hBO = new HospedeBO();
        carregarDados();
        preencherCampos();
        estadoDosCampos();
        
    }    
    
    private void carregarDados() {
        try {
            hospedes = hBO.listar();
        } catch (SQLException ex) {
            mensagemDeErroBD();
        }
    }
    
    private void preencherCampos() {
        if (hospedes.size() > 0) {
            Hospede h = hospedes.get(regAtual);
            txtNome.setText(h.getNome());
            txtRua.setText(h.getEndRua());
            txtNumero.setText(h.getEndNumero());
            txtBairro.setText(h.getEndBairro());
            txtComplemento.setText(h.getEndComplemento());
            txtCidade.setText(h.getEndCidade());
            txtEstado.setText(h.getEndEstado());
            txtPais.setText(h.getEndPais());
            txtCep.setText(h.getEndCep());
            txtCpf.setText(h.getCpf());
            txtRg.setText(h.getRg());
            txtTelefone.setText(h.getTelefone());
            txtCelular.setText(h.getCelular());
            txtEmail.setText(h.getEmail());
            dtDataNascimento.setValue(h.getDataNascimento());
        }
    }
    
    private void limparCampos() {
        txtNome.clear();
        txtRua.clear();
        txtNumero.clear();
        txtBairro.clear();
        txtComplemento.clear();
        txtCidade.clear();
        txtEstado.clear();
        txtPais.clear();
        txtCep.clear();
        txtCpf.clear();
        txtRg.clear();
        txtTelefone.clear();
        txtCelular.clear();
        txtEmail.clear();
        dtDataNascimento.setValue(null);
    }
    
    private void mensagemDeErroBD() {
        mensagemDeErro("Erro de comunicação com "
                + "o Banco de Dado!\nProcure o administrador "
                + "do sistema");
    }
    
    private void mensagemDeErro(String mensagem) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO");
        a.setHeaderText(null);
        a.setContentText(mensagem);
        a.showAndWait();
    }
    
    private void estadoDosCampos() {
        txtNome.setDisable(acao == SEM_ACAO);
        txtRua.setDisable(acao == SEM_ACAO);
        txtNumero.setDisable(acao == SEM_ACAO);
        txtBairro.setDisable(acao == SEM_ACAO);
        txtCidade.setDisable(acao == SEM_ACAO);
        txtComplemento.setDisable(acao == SEM_ACAO);
        txtEstado.setDisable(acao == SEM_ACAO);
        txtCidade.setDisable(acao == SEM_ACAO);
        txtPais.setDisable(acao == SEM_ACAO);
        txtRg.setDisable(acao == SEM_ACAO);
        txtCpf.setDisable(acao == SEM_ACAO);
        txtTelefone.setDisable(acao == SEM_ACAO);
        txtCelular.setDisable(acao == SEM_ACAO);
        txtEmail.setDisable(acao == SEM_ACAO);
        dtDataNascimento.setDisable(acao == SEM_ACAO);
    }

    @FXML
    private void novoRegistro(ActionEvent event) {
        estadoDosCampos();
    }

    @FXML
    private void editarRegistro(ActionEvent event) {
    }

    @FXML
    private void apagarRegistro(ActionEvent event) {
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) {
    }

    @FXML
    private void salvarRegistro(ActionEvent event) {
    }

    @FXML
    private void buscarDados(ActionEvent event) {
    }
}
