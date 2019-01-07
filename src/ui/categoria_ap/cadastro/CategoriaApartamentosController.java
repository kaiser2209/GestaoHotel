/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.categoria_ap.cadastro;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import exceptions.CategoriaApartamentoExistenteException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import model.categoriaApartamento.CategoriaApartamentoBO;
import model.entidades.CategoriaApartamento;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class CategoriaApartamentosController implements Initializable {
    public static final int SEM_ACAO = 0, INCLUSAO = 1, EDICAO = 2;
    private ArrayList<CategoriaApartamento> categorias = new ArrayList<>();
    private CategoriaApartamentoBO cBO;
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
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private JFXTextArea txtDescricao;
    @FXML
    private NumberValidator valNumber;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNome;
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
    private NumberValidator somenteNumeros;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarValidacao();
        definirValidacao();
        cBO = new CategoriaApartamentoBO();
        regAtual = 0;
        carregarDados();
        estadoDoRegistro();
        atualizarNumeroRegistro();
    } 
    
    private void atualizarNumeroRegistro() {
        txtRegistroAtual.setText(String.valueOf(regAtual + 1));
        lblTotalRegistros.setText("/" + String.valueOf(categorias.size()));
    }
    
    private void estadoDoRegistro() {
        estadoDosBotoes();
        estadoDosCampos();
    }
    
    private void estadoDosBotoes() {
        btnNovo.setDisable(acao != SEM_ACAO);
        btnEditar.setDisable(acao != SEM_ACAO);
        btnExcluir.setDisable(acao != SEM_ACAO);
        btnSalvar.setDisable(acao == SEM_ACAO);
        btnCancelar.setDisable(acao == SEM_ACAO);
        btnAnterior.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnPrimeiro.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnProximo.setDisable((regAtual >= categorias.size() - 1) || acao != SEM_ACAO);
        btnUltimo.setDisable((regAtual >= categorias.size() - 1) || acao != SEM_ACAO);
    }
    
    private void estadoDosCampos() {
        txtCodigo.setEditable(acao != SEM_ACAO);
        txtNome.setEditable(acao != SEM_ACAO);
        txtDescricao.setEditable(acao != SEM_ACAO);
        txtValor.setEditable(acao != SEM_ACAO);
    }
    
    private void carregarDados() {
        try {
            categorias = cBO.listar();
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
        }
        
        preencherCampos();
    }

    private void preencherCampos() {
        if (categorias.size() > 0) {
            txtId.setText(String.valueOf(categorias.get(regAtual).getId()));
            txtNome.setText(categorias.get(regAtual).getNomeCategoria());
            txtCodigo.setText(categorias.get(regAtual).getCodigo());
            txtDescricao.setText(categorias.get(regAtual).getDescricao());
            txtValor.setText(categorias.get(regAtual).getFormattedValorDiaria());
        }
    }
    
    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtDescricao.clear();
        txtValor.clear();
        txtCodigo.clear();
    }
    
    private void atualizarDados() {
        carregarDados();
        estadoDoRegistro();
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
                cBO.excluir(categorias.get(regAtual));
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1451) {
                    Mensagem.mensagemDeErro("Não foi possível excluir a categoria "
                            + "selecionada!\nVerifique se não existe algum apartamento "
                            + "cadastrado com esta categoria e tente novamente!");
                } else {
                    Mensagem.mensagemDeErro(ex);
                }
            }
            if (regAtual >= categorias.size() - 1) {
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
        if (validate()) {
            try {
                CategoriaApartamento c = new CategoriaApartamento();
                c.setCodigo(txtCodigo.getText());
                c.setNomeCategoria(txtNome.getText());
                c.setDescricao(txtDescricao.getText());
                c.setValorDiaria(txtValor.getText());
                if (acao == EDICAO) {
                    c.setId(Integer.parseInt(txtId.getText()));
                    cBO.editar(c);
                } else if (acao == INCLUSAO) {

                    cBO.salvar(c);

                    regAtual = categorias.size();
                }
                acao = SEM_ACAO;
                atualizarDados();
                atualizarNumeroRegistro();
            } catch (SQLException e) {
                Mensagem.mensagemDeErroBD();
                e.printStackTrace();
            } catch (CategoriaApartamentoExistenteException e1) {
                Mensagem.mensagemDeErro("Já existe uma categoria cadastrada com "
                        + "o código informado!");
            } catch (ParseException e2) {
                Mensagem.mensagemDeErro("O número informado não é válido!");
            }
        } else {
            Mensagem.mensagemDeErro("Não foi possível salvar os dados.\n"
                    + "Verifique os valores digitados e tente novamente!");
        }
    }

    @FXML
    private void validarValor(ActionEvent event) {

    }
    
    private void recarregarDados() {
        preencherCampos();
        estadoDosBotoes();
        atualizarNumeroRegistro();
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
        regAtual = categorias.size() - 1;
        recarregarDados();
    }
    
    private void inicializarValidacao() {
        campoObrigatorio = new RequiredFieldValidator();
        campoObrigatorio.setMessage("Obrigatório");
        somenteNumeros = new NumberValidator();
        somenteNumeros.setMessage("Inválido");
    }
    
    private void definirValidacao() {
        txtCodigo.setValidators(campoObrigatorio);
        txtNome.setValidators(campoObrigatorio);
        txtValor.setValidators(campoObrigatorio, somenteNumeros);
        
    }
    
    private boolean validate() {
        txtCodigo.validate();
        txtNome.validate();
        txtValor.validate();
                
        return txtCodigo.validate() && txtNome.validate() && txtValor.validate();
    }
}
