/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.funcao.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import exceptions.FuncaoExistenteException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Funcao;
import model.funcao.FuncaoBO;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class CadastroFuncaoController implements Initializable {
    public static final int SEM_ACAO = 0, INCLUSAO = 1, EDICAO = 2;
    private ArrayList<Funcao> funcoes = new ArrayList<>();
    private FuncaoBO fBO;
    private int regAtual = -1;
    private int acao = SEM_ACAO;
    
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
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextArea txtDescricao;
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
    private JFXComboBox<Short> cboNivelAcesso;
    private RequiredFieldValidator campoRequerido;
    @FXML
    private RequiredFieldValidator valorObrigatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarValidacoes();
        definirValidacoes();
        cboNivelAcesso.getItems().addAll((short) 0, (short) 1, (short) 2, (short) 3, (short) 4, (short) 5);
        fBO = new FuncaoBO();
        regAtual = 0;
        carregarDados();
        estadoDoRegistro();
        atualizarNumeroRegistro();
    }  
    
    private void inicializarValidacoes() {
        campoRequerido = new RequiredFieldValidator();
        campoRequerido.setMessage("Campo obrigatório");
        
        
    }
    
    private void definirValidacoes() {
        txtNome.setValidators(campoRequerido);
        cboNivelAcesso.setValidators(campoRequerido);
    }
    
    private boolean verificarValidacao() {
        txtNome.validate();
        cboNivelAcesso.validate();
        return txtNome.validate() && cboNivelAcesso.validate();
    }
    
    private void atualizarDados() {
        carregarDados();
        estadoDoRegistro();
    }
    
    private void estadoDoRegistro() {
        estadoDosBotoes();
        estadoDosCampos();
    }
    
    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtDescricao.clear();
        cboNivelAcesso.setValue(null);
    }
    
    private void carregarDados() {
        try {
            funcoes = fBO.listar();
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
        
        preencherCampos();
    }
    
    private void estadoDosBotoes() {
        btnNovo.setDisable(acao != SEM_ACAO);
        btnEditar.setDisable(acao != SEM_ACAO);
        btnExcluir.setDisable(acao != SEM_ACAO);
        btnSalvar.setDisable(acao == SEM_ACAO);
        btnCancelar.setDisable(acao == SEM_ACAO);
        btnAnterior.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnPrimeiro.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnProximo.setDisable((regAtual >= funcoes.size() - 1) || acao != SEM_ACAO);
        btnUltimo.setDisable((regAtual >= funcoes.size() - 1) || acao != SEM_ACAO);
    }
    
    private void estadoDosCampos() {
        txtNome.setEditable(acao != SEM_ACAO);
        txtDescricao.setEditable(acao != SEM_ACAO);
        cboNivelAcesso.setDisable(acao == SEM_ACAO);
    }
    
    private void preencherCampos() {
        if (funcoes.size() > 0) {
            txtId.setText(String.valueOf(funcoes.get(regAtual).getId()));
            txtNome.setText(funcoes.get(regAtual).getNomeFuncao());
            txtDescricao.setText(funcoes.get(regAtual).getDescricaoFuncao());
            cboNivelAcesso.setValue(funcoes.get(regAtual).getNivelAcesso());
        }
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
                fBO.excluir(funcoes.get(regAtual));
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1451) {
                    Mensagem.mensagemDeErro("Não foi possível excluir a função "
                            + "selecionada!\nVerifique se não existe algum usuário "
                            + "cadastrado com esta função antes de continuar.");
                } else {
                    Mensagem.mensagemDeErro(ex);
                }
                
            }
            if (regAtual >= funcoes.size() - 1) {
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
        
        if (verificarValidacao()) {
            Funcao f = new Funcao();
            f.setNomeFuncao(txtNome.getText());
            f.setDescricaoFuncao(txtDescricao.getText());
            f.setNivelAcesso(cboNivelAcesso.getValue());

            try {
                if (acao == EDICAO) {
                    f.setId(Integer.parseInt(txtId.getText()));
                    fBO.editar(f);
                } else if (acao == INCLUSAO) {

                    fBO.salvar(f);

                    regAtual = funcoes.size();
                }
                acao = SEM_ACAO;
                atualizarDados();
            } catch (SQLException e) {
                Mensagem.mensagemDeErro(e);
            } catch (FuncaoExistenteException e1) {
                Mensagem.mensagemDeErro("A função informada já está cadastrada!");
            }
            Mensagem.mensagemDeErro("Registro incluído com sucesso!");
            atualizarNumeroRegistro();
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
        regAtual = funcoes.size() - 1;
        recarregarDados();
    }
    
    private void recarregarDados() {
        preencherCampos();
        estadoDosBotoes();
        atualizarNumeroRegistro();
    }    
    
    private void atualizarNumeroRegistro() {
        txtRegistroAtual.setText(String.valueOf(regAtual + 1));
        lblTotalRegistros.setText(String.valueOf(funcoes.size()));
    }
}
