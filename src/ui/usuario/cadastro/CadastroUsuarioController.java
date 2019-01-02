/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.usuario.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Funcao;
import model.entidades.Usuario;
import model.usuario.UsuarioBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class CadastroUsuarioController implements Initializable {
    public final static int SEM_ACAO = 0, INCLUSAO = 1, EDICAO = 2;
    private UsuarioBO uBO;
    private ArrayList<Usuario> usuarios;
    private int acao = SEM_ACAO;
    private int regAtual = -1;
    private boolean filtroAtivo;
    
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnBuscar;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtRg;
    @FXML
    private JFXTextField txtCpf;
    @FXML
    private JFXTextField txtCep;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtBairro;
    @FXML
    private JFXTextField txtComplemento;
    @FXML
    private JFXTextField txtCidade;
    @FXML
    private JFXTextField txtEstado;
    @FXML
    private JFXTextField txtPais;
    @FXML
    private JFXTextField txtTelefone;
    @FXML
    private JFXTextField txtCelular;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXDatePicker dtDataNascimento;
    @FXML
    private JFXComboBox<Funcao> cboFuncao;
    @FXML
    private Button btnPrimeiro;
    @FXML
    private Button btnAnterior;
    @FXML
    private TextField txtRegistroAtual;
    @FXML
    private Label lblTotalRegistros;
    @FXML
    private Button btnProximo;
    @FXML
    private Button btnUltimo;
    @FXML
    private JFXTextField txtRua;
    @FXML
    private JFXTextField txtId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uBO = new UsuarioBO();
        ObservableList<Funcao> funcoes = FXCollections.observableArrayList();
        cboFuncao.setItems(funcoes);

    }    

    @FXML
    private void novoRegistro(ActionEvent event) {
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

    @FXML
    private void primeiroRegistro(ActionEvent event) {
    }

    @FXML
    private void registroAnterior(ActionEvent event) {
    }

    @FXML
    private void proximoRegistro(ActionEvent event) {
    }

    @FXML
    private void ultimoRegistro(ActionEvent event) {
    }
    
    private void limparCampos() {
        txtId.clear();
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
    
    private void estadoDosCampos() {
        txtNome.setEditable(acao != SEM_ACAO);
        txtRua.setEditable(acao != SEM_ACAO);
        txtNumero.setEditable(acao != SEM_ACAO);
        txtBairro.setEditable(acao != SEM_ACAO);
        txtCidade.setEditable(acao != SEM_ACAO);
        txtComplemento.setEditable(acao != SEM_ACAO);
        txtEstado.setEditable(acao != SEM_ACAO);
        txtCidade.setEditable(acao != SEM_ACAO);
        txtCep.setEditable(acao != SEM_ACAO);
        txtPais.setEditable(acao != SEM_ACAO);
        txtRg.setEditable(acao != SEM_ACAO);
        txtCpf.setEditable(acao != SEM_ACAO);
        txtTelefone.setEditable(acao != SEM_ACAO);
        txtCelular.setEditable(acao != SEM_ACAO);
        txtEmail.setEditable(acao != SEM_ACAO);
        dtDataNascimento.setDisable(acao == SEM_ACAO);
        txtId.setEditable(acao == SEM_ACAO);
    }
    
    private void estadoBotoes() {
        btnNovo.setDisable(acao != SEM_ACAO);
        btnEditar.setDisable(acao != SEM_ACAO || usuarios.isEmpty());
        btnExcluir.setDisable(acao != SEM_ACAO || usuarios.isEmpty());
        btnCancelar.setDisable(acao == SEM_ACAO);
        btnSalvar.setDisable(acao == SEM_ACAO);
        btnBuscar.setDisable(acao != SEM_ACAO || usuarios.isEmpty());
        btnPrimeiro.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnAnterior.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnProximo.setDisable(regAtual >= (usuarios.size() - 1) || acao != SEM_ACAO);
        btnUltimo.setDisable(regAtual >= (usuarios.size() - 1) || acao != SEM_ACAO);
        if (filtroAtivo) {
            btnBuscar.setText("Limpar");
        } else {
            btnBuscar.setText("Filtrar");
        }
    }
    
    private void preencherCampos() {
        txtRegistroAtual.setText(String.valueOf(regAtual + 1));
        lblTotalRegistros.setText("/" + String.valueOf(usuarios.size()));
        if (filtroAtivo) {
            lblTotalRegistros.setText(lblTotalRegistros.getText() + "*");
        }
        if (usuarios.size() > 0) {
            Usuario u = usuarios.get(regAtual);
            txtId.setText(String.valueOf(u.getIdUsuario()));
            txtNome.setText(u.getNome());
            txtRua.setText(u.getEndRua());
            txtNumero.setText(u.getEndNumero());
            txtBairro.setText(u.getEndBairro());
            txtComplemento.setText(u.getEndComplemento());
            txtCidade.setText(u.getEndCidade());
            txtEstado.setText(u.getEndEstado());
            txtPais.setText(u.getEndPais());
            txtCep.setText(u.getEndCep());
            txtCpf.setText(u.getCpf());
            txtRg.setText(u.getRg());
            txtTelefone.setText(u.getTelefone());
            txtCelular.setText(u.getCelular());
            txtEmail.setText(u.getEmail());
            dtDataNascimento.setValue(u.getDataNascimento());
        }
    }
    
}
