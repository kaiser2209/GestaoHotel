/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.usuario.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import exceptions.UsuarioExistenteException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private RequiredFieldValidator campoObrigatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarValidacao();
        definirValidacao();
        uBO = new UsuarioBO();
        try {
            cboFuncao.setItems(uBO.listarFuncaoCrescente());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
        regAtual = 0;
        carregarDados();
        estadoDoRegistro();
        atualizarNumeroRegistro();
    }    

    @FXML
    private void novoRegistro(ActionEvent event) {
        acao = INCLUSAO;
        limparCampos();
        estadoDoRegistro();
    }

    @FXML
    private void editarRegistro(ActionEvent event) {
        acao = EDICAO;
        estadoDoRegistro();
    }

    @FXML
    private void apagarRegistro(ActionEvent event) {
        Optional<ButtonType> op = Mensagem.mensagemDeConfirmacao("Deseja excluir?", "Exclusão");
        if (op.get() == ButtonType.OK) {
            try {
                uBO.excluir(usuarios.get(regAtual));
            } catch (SQLException ex) {
                Mensagem.mensagemDeErroBD();
            }
            if (regAtual >= usuarios.size() - 1) {
                regAtual--;
            }
        }
        atualizarDados();
        atualizarNumeroRegistro();
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) {
        acao = SEM_ACAO;
        atualizarDados();
    }

    @FXML
    private void salvarRegistro(ActionEvent event) {
        removerEspacos();
        if (validate()) {
            Usuario u = new Usuario();
            u.setNome(txtNome.getText());
            u.setEndRua(txtRua.getText());
            u.setEndNumero(txtNumero.getText());
            u.setEndBairro(txtBairro.getText());
            u.setEndComplemento(txtComplemento.getText());
            u.setEndCidade(txtCidade.getText());
            u.setEndCep(txtCep.getText());
            u.setEndEstado(txtEstado.getText());
            u.setEndPais(txtPais.getText());
            u.setTelefone(txtTelefone.getText());
            u.setCelular(txtCelular.getText());
            u.setEmail(txtEmail.getText());
            u.setRg(txtRg.getText());
            u.setCpf(txtCpf.getText());
            u.setDataNascimento(dtDataNascimento.getValue());
            u.setFuncao(cboFuncao.getValue());

            try {
                if (acao == EDICAO) {
                    u.setIdUsuario(Integer.parseInt(txtId.getText()));
                    u.setDataCadastro(usuarios.get(regAtual).getDataCadastro());
                    uBO.editar(u);
                } else if (acao == INCLUSAO) {
                    u.setDataCadastro(LocalDateTime.now());
                    uBO.salvar(u);

                    regAtual = usuarios.size();
                }
                acao = SEM_ACAO;
                atualizarDados();
                atualizarNumeroRegistro();
            } catch (SQLException e) {
                Mensagem.mensagemDeErroBD();
                e.printStackTrace();
            } catch (UsuarioExistenteException e1) {
                Mensagem.mensagemDeErro("Já existe um Usuário cadastrado com este CPF!");
                e1.printStackTrace();
            }
        } else {
            Mensagem.mensagemCamposInvalidos();
        }
    }

    @FXML
    private void buscarDados(ActionEvent event) throws IOException {
        if (filtroAtivo) {
            filtroAtivo = false;
            regAtual = 0;
            carregarDados();
            preencherCampos();
            estadoDosBotoes();
            atualizarNumeroRegistro();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/usuario/cadastro/BuscaUsuario.fxml"));
            Parent root = loader.load();
            Scene cena = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Pesquisar Usuário");
            stage.setScene(cena);
            stage.initModality(Modality.APPLICATION_MODAL);
            BuscaUsuarioController controller = (BuscaUsuarioController) loader.getController();
            stage.showAndWait();
            if (controller.get() == BuscaUsuarioController.BUSCA_OK) {
                String campo = controller.getCampo();
                String valor = controller.getTextoBusca();
                ArrayList<Usuario> lista = new ArrayList<>();
                try {
                    if (campo.equals("CPF")) {
                        lista = uBO.filtrarPeloCpf(valor);

                    } else if (campo.equals("Nome")) {
                        lista = uBO.filtrarPeloNome(valor);
                    }
                    if (lista.size() > 0) {
                        usuarios = lista;
                        regAtual = 0;
                        filtroAtivo = true;
                        limparCampos();
                        preencherCampos();
                        atualizarEstadoRegistro();
                        atualizarNumeroRegistro();
                    } else {
                        Mensagem.mensagemDeErro("Não foram encontrados registros com o critério informado!");
                    }

                } catch (SQLException e) {
                    Mensagem.mensagemDeErroBD();
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void primeiroRegistro(ActionEvent event) {
        regAtual = 0;
        recarregarDados();
    }

    @FXML
    private void registroAnterior(ActionEvent event) {
        regAtual--;
        recarregarDados();
    }

    @FXML
    private void proximoRegistro(ActionEvent event) {
        regAtual++;
        recarregarDados();
    }

    @FXML
    private void ultimoRegistro(ActionEvent event) {
        regAtual = usuarios.size() - 1;
        recarregarDados();
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
        cboFuncao.getSelectionModel().clearSelection();
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
        cboFuncao.setDisable(acao == SEM_ACAO);
        dtDataNascimento.setDisable(acao == SEM_ACAO);
    }
    
    private void estadoDosBotoes() {
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
            cboFuncao.setValue(u.getFuncao());
        }
    }
    
    private void atualizarNumeroRegistro() {
        txtRegistroAtual.setText(String.valueOf(regAtual + 1));
        lblTotalRegistros.setText("/" + String.valueOf(usuarios.size()));
        if (filtroAtivo) {
            lblTotalRegistros.setText(lblTotalRegistros.getText() + "*");
        }
    }
    
    private void recarregarDados() {
        preencherCampos();
        estadoDosBotoes();
        atualizarNumeroRegistro();
    } 
    
    private void estadoDoRegistro() {
        estadoDosBotoes();
        estadoDosCampos();
    }
    
    private void atualizarDados() {
        carregarDados();
        estadoDoRegistro();
        
    }
    
    private void carregarDados() {
        try {
            usuarios = uBO.listar();
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
            ex.printStackTrace();
        }
        
        preencherCampos();
    }
    
    private void atualizarEstadoRegistro() {
        estadoDosCampos();
        estadoDosBotoes();
    }
    
    private void inicializarValidacao() {
        campoObrigatorio = new RequiredFieldValidator();
        campoObrigatorio.setMessage("Obrigatório");
    }
    
    private void definirValidacao() {
        txtNome.setValidators(campoObrigatorio);
        txtRg.setValidators(campoObrigatorio);
        txtCpf.setValidators(campoObrigatorio);
        txtRua.setValidators(campoObrigatorio);
        txtBairro.setValidators(campoObrigatorio);
        txtCidade.setValidators(campoObrigatorio);
        txtEstado.setValidators(campoObrigatorio);
        txtPais.setValidators(campoObrigatorio);
        txtCep.setValidators(campoObrigatorio);
        dtDataNascimento.setValidators(campoObrigatorio);
        cboFuncao.setValidators(campoObrigatorio);
        
    }
    
    private boolean validate() {
        txtNome.validate();
        txtRg.validate();
        txtCpf.validate();
        txtRua.validate();
        txtBairro.validate();
        txtCidade.validate();
        txtEstado.validate();
        txtPais.validate();
        txtCep.validate();
        dtDataNascimento.validate();
        cboFuncao.validate();
                
        return txtNome.validate() && txtRg.validate() && txtCpf.validate() &&
                txtRua.validate() && txtBairro.validate() && txtCidade.validate() &&
                txtEstado.validate() && txtPais.validate() && dtDataNascimento.validate() &&
                txtCep.validate() && cboFuncao.validate();
    }
    
    private void removerEspacos() {
        
        txtNome.setText(txtNome.getText().trim());
        txtRua.setText(txtRua.getText().trim());
        txtNumero.setText(txtNumero.getText().trim());
        txtBairro.setText(txtBairro.getText().trim());
        txtComplemento.setText(txtComplemento.getText().trim());
        txtCidade.setText(txtCidade.getText().trim());
        txtEstado.setText(txtEstado.getText().trim());
        txtPais.setText(txtPais.getText().trim());
        txtCep.setText(txtCep.getText().trim());
        txtCpf.setText(txtCpf.getText().trim());
        txtRg.setText(txtRg.getText().trim());
        txtTelefone.setText(txtTelefone.getText().trim());
        txtCelular.setText(txtCelular.getText().trim());
        txtEmail.setText(txtEmail.getText().trim());
    }
}
