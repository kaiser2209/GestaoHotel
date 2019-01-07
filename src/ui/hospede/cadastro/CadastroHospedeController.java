/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospede.cadastro;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import exceptions.HospedeExistenteException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.entidades.Hospede;
import model.hospede.HospedeBO;
import util.Mensagem;

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
    private int regAtual = -1;
    private boolean filtroAtivo;
    
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
    @FXML
    private Label lblDataNascimento;
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
    private JFXTextField txtId;
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
    private RequiredFieldValidator campoObrigatorio;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarValidacao();
        definirValidacao();
        hBO = new HospedeBO();
        carregarDados();
        if (hospedes.size() > 0) {
            regAtual = 0;
        }
        preencherCampos();
        estadoDosCampos();
        estadoBotoes();
    }    
    
    private void carregarDados() {
        try {
            hospedes = hBO.listar();
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
    }
    
    private void preencherCampos() {
        txtRegistroAtual.setText(String.valueOf(regAtual + 1));
        lblTotalRegistros.setText("/" + String.valueOf(hospedes.size()));
        if (filtroAtivo) {
            lblTotalRegistros.setText(lblTotalRegistros.getText() + "*");
        }
        if (hospedes.size() > 0) {
            Hospede h = hospedes.get(regAtual);
            txtId.setText(String.valueOf(h.getIdHospede()));
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
        btnEditar.setDisable(acao != SEM_ACAO || hospedes.isEmpty());
        btnExcluir.setDisable(acao != SEM_ACAO || hospedes.isEmpty());
        btnCancelar.setDisable(acao == SEM_ACAO);
        btnSalvar.setDisable(acao == SEM_ACAO);
        btnBuscar.setDisable(acao != SEM_ACAO || hospedes.isEmpty());
        btnPrimeiro.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnAnterior.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnProximo.setDisable(regAtual >= (hospedes.size() - 1) || acao != SEM_ACAO);
        btnUltimo.setDisable(regAtual >= (hospedes.size() - 1) || acao != SEM_ACAO);
        if (filtroAtivo) {
            btnBuscar.setText("Limpar");
        } else {
            btnBuscar.setText("Filtrar");
        }
    }
    
    private void atualizarEstadoRegistro() {
        estadoDosCampos();
        estadoBotoes();
    }

    @FXML
    private void novoRegistro(ActionEvent event) {
        acao = INCLUSAO;
        limparCampos();
        atualizarEstadoRegistro();
    }

    @FXML
    private void editarRegistro(ActionEvent event) {
        acao = EDICAO;
        atualizarEstadoRegistro();
    }

    @FXML
    private void apagarRegistro(ActionEvent event) {
        Optional<ButtonType> btn = Mensagem.mensagemDeConfirmacao("Deseja excluir?", "EXCLUIR");
        
        if (btn.get() == ButtonType.OK) {
            try {
                hBO.excluir(hospedes.get(regAtual));
                carregarDados();
                if ((regAtual == hospedes.size())) {
                    regAtual--;
                }
                limparCampos();
                preencherCampos();
            } catch (SQLException ex) {
                Mensagem.mensagemDeErroBD();
            }
        }
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) {
        acao = SEM_ACAO;
        atualizarEstadoRegistro();
        preencherCampos();
    }

    @FXML
    private void salvarRegistro(ActionEvent event) {
        removerEspacos();
        if (validate()) {
            try {
                Hospede h = new Hospede();
                h.setNome(txtNome.getText());
                h.setEndRua(txtRua.getText());
                h.setEndNumero(txtNumero.getText());
                h.setEndBairro(txtBairro.getText());
                h.setEndComplemento(txtComplemento.getText());
                h.setEndCidade(txtCidade.getText());
                h.setEndEstado(txtEstado.getText());
                h.setEndCep(txtCep.getText());
                h.setEndPais(txtPais.getText());
                h.setTelefone(txtTelefone.getText());
                h.setCelular(txtCelular.getText());
                h.setEmail(txtEmail.getText());
                h.setDataNascimento(dtDataNascimento.getValue());
                h.setCpf(txtCpf.getText());
                h.setRg(txtRg.getText());
                if (acao == EDICAO) {
                    Optional<ButtonType> btn = Mensagem.mensagemDeConfirmacao("Deseja salvar as alterações?", "EDITAR");
                    if (btn.get() == ButtonType.OK) {
                        h.setIdHospede(Integer.parseInt(txtId.getText()));
                        hBO.editar(h);
                        hospedes.remove(regAtual);
                        hospedes.add(regAtual, h);
                    }
                } else if (acao == INCLUSAO) {
                    h.setDataCadastro(LocalDateTime.now());
                    hBO.salvar(h);
                    carregarDados();
                    if (filtroAtivo) {
                        filtroAtivo = false;
                    }
                    regAtual = hospedes.size() - 1;
                }
                acao = SEM_ACAO;
                preencherCampos();
                atualizarEstadoRegistro();
            } catch (SQLException e) {
                Mensagem.mensagemDeErro(e);
            } catch (HospedeExistenteException e1) {
                Mensagem.mensagemDeErro("Já existe um hóspdede cadastrado com "
                        + "o cpf informado.");
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
            estadoBotoes();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospede/cadastro/BuscaHospede.fxml"));
            Parent root = loader.load();
            Scene cena = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Pesquisar Hóspede");
            stage.setScene(cena);
            stage.initModality(Modality.APPLICATION_MODAL);
            BuscaHospedeController controller = (BuscaHospedeController) loader.getController();
            stage.showAndWait();
            if (controller.get() == BuscaHospedeController.BUSCA_OK) {
                String campo = controller.getCampo();
                String valor = controller.getTextoBusca();
                ArrayList<Hospede> lista = new ArrayList<>();
                try {
                    if (campo.equals("CPF")) {
                        lista = hBO.listarPorCpf(valor);

                    } else if (campo.equals("Nome")) {
                        lista = hBO.listarPorNome(valor);
                    }
                    if (lista.size() > 0) {
                        hospedes = lista;
                        regAtual = 0;
                        filtroAtivo = true;
                        limparCampos();
                        preencherCampos();
                        estadoBotoes();
                        atualizarEstadoRegistro();
                        atualizarNumeroRegistro();
                    } else {
                        Mensagem.mensagemDeErro("Não foram encontrados registros com o critério informado!");
                    }

                } catch (SQLException e) {
                    Mensagem.mensagemDeErroBD();
                }
            }
        }
    }

    @FXML
    private void primeiroRegistro(ActionEvent event) {
        regAtual = 0;
        preencherCampos();
        estadoBotoes();
    }

    @FXML
    private void registroAnterior(ActionEvent event) {
        if (regAtual > 0) {
            regAtual--;
        }
        preencherCampos();
        estadoBotoes();
    }

    @FXML
    private void proximoRegistro(ActionEvent event) {
        if (regAtual < hospedes.size() - 1) {
            regAtual++;
        }
        preencherCampos();
        estadoBotoes();
    }

    @FXML
    private void ultimoRegistro(ActionEvent event) {
        regAtual = hospedes.size() - 1;
        preencherCampos();
        estadoBotoes();
    }
    
    private void atualizarNumeroRegistro() {
        txtRegistroAtual.setText(String.valueOf(regAtual + 1));
        lblTotalRegistros.setText("/" + String.valueOf(hospedes.size()));
        if (filtroAtivo) {
            lblTotalRegistros.setText(lblTotalRegistros.getText() + "*");
        }
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
                
        return txtNome.validate() && txtRg.validate() && txtCpf.validate() &&
                txtRua.validate() && txtBairro.validate() && txtCidade.validate() &&
                txtEstado.validate() && txtPais.validate() && dtDataNascimento.validate() &&
                txtCep.validate();
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
