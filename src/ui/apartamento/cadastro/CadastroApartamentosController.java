/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.apartamento.cadastro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import exceptions.ApartamentoExistenteException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.apartamento.ApartamentoBO;
import model.entidades.Apartamento;
import model.entidades.CategoriaApartamento;
import util.Mensagem;

/**
 * FXML Controller class
 *
 * @author guard
 */
public class CadastroApartamentosController implements Initializable {
    public final static int SEM_ACAO = 0, INCLUSAO = 1, EDICAO = 2;
    private ApartamentoBO aBO;
    private ArrayList<Apartamento> apartamentos;
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
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtRamal;
    @FXML
    private JFXComboBox<CategoriaApartamento> cboCategoria;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        aBO = new ApartamentoBO();
        try {
            cboCategoria.setItems(aBO.listarCategoriaCrescente());
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
            ex.printStackTrace();
        }
        regAtual = 0;
        carregarDados();
        estadoDoRegistro();
        atualizarNumeroRegistro();
    }   
    
    private void carregarDados() {
        try {
            apartamentos = aBO.listar();
        } catch (SQLException ex) {
            Mensagem.mensagemDeErroBD();
            ex.printStackTrace();
        }
        
        preencherCampos();
    }
    
    private void preencherCampos() {
     
        if (apartamentos.size() > 0) {
            Apartamento a = apartamentos.get(regAtual);
            txtId.setText(String.valueOf(a.getId()));
            txtNumero.setText(a.getNumero());
            txtRamal.setText(String.valueOf(a.getRamal()));
            cboCategoria.setValue(a.getCategoria());
        }
    }
    
    private void estadoDoRegistro() {
        estadoDosBotoes();
        estadoDosCampos();
    }
    
    private void estadoDosBotoes() {
        btnNovo.setDisable(acao != SEM_ACAO);
        btnEditar.setDisable(acao != SEM_ACAO || apartamentos.isEmpty());
        btnExcluir.setDisable(acao != SEM_ACAO || apartamentos.isEmpty());
        btnCancelar.setDisable(acao == SEM_ACAO);
        btnSalvar.setDisable(acao == SEM_ACAO);
        btnBuscar.setDisable(acao != SEM_ACAO || apartamentos.isEmpty());
        btnPrimeiro.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnAnterior.setDisable(regAtual < 1 || acao != SEM_ACAO);
        btnProximo.setDisable(regAtual >= (apartamentos.size() - 1) || acao != SEM_ACAO);
        btnUltimo.setDisable(regAtual >= (apartamentos.size() - 1) || acao != SEM_ACAO);
        if (filtroAtivo) {
            btnBuscar.setText("Limpar");
        } else {
            btnBuscar.setText("Filtrar");
        }
    }
    
    private void estadoDosCampos() {
        txtNumero.setEditable(acao != SEM_ACAO);
        txtRamal.setEditable(acao != SEM_ACAO);
        cboCategoria.setDisable(acao == SEM_ACAO);
    }
    
    private void atualizarNumeroRegistro() {
        txtRegistroAtual.setText(String.valueOf(regAtual + 1));
        lblTotalRegistros.setText("/" + String.valueOf(apartamentos.size()));
        if (filtroAtivo) {
            lblTotalRegistros.setText(lblTotalRegistros.getText() + "*");
        }
    }
    
    private void atualizarEstadoRegistro() {
        estadoDosCampos();
        estadoDosBotoes();
    }
    
    private void atualizarDados() {
        carregarDados();
        estadoDoRegistro();
        
    }
    
    private void recarregarDados() {
        preencherCampos();
        estadoDosBotoes();
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
                aBO.excluir(apartamentos.get(regAtual));
            } catch (SQLException ex) {
                Mensagem.mensagemDeErroBD();
            }
            if (regAtual >= apartamentos.size() - 1) {
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
        Apartamento a = new Apartamento();
        a.setNumero(txtNumero.getText());
        a.setRamal(Integer.parseInt(txtRamal.getText()));
        a.setCategoria(cboCategoria.getValue());
        
        try {
            if (acao == EDICAO) {
                a.setId(Integer.parseInt(txtId.getText()));
                aBO.editar(a);
            } else if (acao == INCLUSAO) {
                aBO.salvar(a);

                regAtual = apartamentos.size();
            }
            acao = SEM_ACAO;
            atualizarDados();
            atualizarNumeroRegistro();
        } catch (SQLException e) {
            Mensagem.mensagemDeErroBD();
            e.printStackTrace();
        } catch (ApartamentoExistenteException e1) {
            Mensagem.mensagemDeErro("Já existe um Apartamento cadastrado com este número!");
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
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/apartamento/cadastro/BuscaApartamento.fxml"));
            Parent root = loader.load();
            Scene cena = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Pesquisar Apartamento");
            stage.setScene(cena);
            stage.initModality(Modality.APPLICATION_MODAL);
            BuscaApartamentoController controller = (BuscaApartamentoController) loader.getController();
            stage.showAndWait();
            if (controller.get() == BuscaApartamentoController.BUSCA_OK) {
                String valor = controller.getTextoBusca();
                try {
                    ArrayList<Apartamento> lista = aBO.filtrarPeloNumero(valor);
                    if (lista.size() > 0) {
                        apartamentos = lista;

                        regAtual = 0;
                        filtroAtivo = true;
                        limparCampos();
                        preencherCampos();
                        estadoDosBotoes();
                        atualizarEstadoRegistro();
                        atualizarNumeroRegistro();
                    } else {
                        Mensagem.mensagemDeErro("Não foi encontrado registros com o critério informado!");
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
        regAtual = apartamentos.size() - 1;
        recarregarDados();
    }

    private void limparCampos() {
        txtId.clear();
        txtNumero.clear();
        txtRamal.clear();
        cboCategoria.setValue(null);
    }
    
}
