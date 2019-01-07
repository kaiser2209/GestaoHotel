/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.hospedagem.checkin;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.apartamento.ApartamentoBO;
import model.entidades.Apartamento;
import model.entidades.Hospedagem;
import model.entidades.Hospede;
import model.hospedagem.HospedagemBO;
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
    private ArrayList<Hospede> hospedes;
    private ObservableList<Hospede> dadosTabela;
    private HospedagemBO hBO;
    private static ArrayList<Hospede> hospedesSelecionados;
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
    @FXML
    private JFXTextField txtDiaria;
    private RequiredFieldValidator campoObrigatorio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarValidacao();
        definirValidacao();
        // TODO
        hospedes = new ArrayList<Hospede>();
        aBO = new ApartamentoBO();
        hBO = new HospedagemBO();
        hospedesSelecionados = new ArrayList<>();
        configurarTabela();
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
                mostrarHospedeSelecionado();
                hospedesSelecionados.add(hospede);
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
        if (controller.get() != null) {
            hospede = controller.get();
            hospedesSelecionados.add(hospede);
        }
        mostrarHospedeSelecionado();
    }
    
    private void mostrarHospedeSelecionado() {
        if (hospede != null) {
            txtCpf.setText(hospede.getCpf());
            txtHospede.setText(hospede.getNome());
            txtCelular.setText(hospede.getCelular());
            txtEmail.setText(hospede.getEmail());
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
        if (apartamento != null) {
            calcularDiaria();
        }
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
        colNascimento.setCellValueFactory(new PropertyValueFactory<Hospede, LocalDate>("dataNascimentoFormatada"));
        colCelular.setCellValueFactory(new PropertyValueFactory<Hospede, String>("celular"));

        //Configurando a largura das colunas da tabela
        tabelaHospedes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNome.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 20% width
        colNascimento.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 20% width
        colCelular.setMaxWidth(1f * Integer.MAX_VALUE * 25); // 20% width

        //Adiciona as colunas na tabela na ordem que devem aparecer
        tabelaHospedes.getColumns().addAll(colNome, colNascimento, colCelular);

    }
    
    private void carregarDadosTabela() {
        dadosTabela = FXCollections.observableArrayList(hospedes);
        tabelaHospedes.setItems(dadosTabela);
    }

    @FXML
    private void adicionarHospede(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hospedagem/checkin/BuscaHospede.fxml"));
        Parent root = loader.load();
        Scene cena = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Seleção de Hóspede");
        stage.setScene(cena);
        stage.initModality(Modality.APPLICATION_MODAL);
        BuscaHospedeController controller = (BuscaHospedeController) loader.getController();
        stage.showAndWait();
        if (controller.get() != null) {
            hospedes.add(controller.get());
            hospedesSelecionados.add(controller.get());
        }
        carregarDadosTabela();
        calcularDiaria();
    }

    @FXML
    private void removerHospede(ActionEvent event) throws IOException {
        Hospede h = tabelaHospedes.getSelectionModel().getSelectedItem();
        if (h != null) {
            Optional<ButtonType> op = Mensagem.mensagemDeConfirmacao("Deseja excluir o hóspede?", "Exclusão");
            if (op.get() == ButtonType.OK) {
                hospedes.remove(h);
                hospedesSelecionados.remove(h);
                carregarDadosTabela();
                calcularDiaria();
            }
        } else {
            Mensagem.mensagemDeErro("Selecione um hóspede primeiro e depois clique"
                    + " em Remover Hóspede");
        }
    }

    @FXML
    private void salvarHospedagem(ActionEvent event) throws ParseException {
        if (validate()) {
            hospedes.add(0, hospede);
            Hospedagem h = new Hospedagem();
            h.setHospedes(hospedes);
            h.setDataEntrada(LocalDate.now());
            h.setApartamento(apartamento);
            h.setProcedencia(txtProcedencia.getText());
            h.setDestino(txtDestino.getText());
            NumberFormat nf = NumberFormat.getInstance();
            h.setDiaria(nf.parse(txtDiaria.getText().replace("R$ ", "")).floatValue());

            try {
                hBO.salvar(h);
            } catch (SQLException ex) {
                Mensagem.mensagemDeErro(ex);
            }
            Mensagem.mensagem("Check In Realizado com Sucesso!");
            close((Button) event.getSource());
        } else {
            Mensagem.mensagemCamposInvalidos();
        }
    }

    @FXML
    private void cancelarHospedagem(ActionEvent event) {
        
        close((Button) event.getSource());
    }
    
    private void close(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    
    private void calcularDiaria() {
        Float diaria = apartamento.getCategoria().getValorDiaria();
        DecimalFormat df = new DecimalFormat("R$ #.00");
        txtDiaria.setText(df.format(diaria * acrescimoPorPessoa(hospedes.size())));
    }
    
    private float acrescimoPorPessoa(int n) {
        switch (n) {
            case 0:
                return 1.0f;
            case 1:
                return 1.8f;
            case 2:
                return 2.4f;
            case 3:
                return 3.0f;
            default:
                return 1.0f;
        }
    }
    
    public static ArrayList<Hospede> getHospedesSelecionados() {
        return hospedesSelecionados;
    }
    
    private void inicializarValidacao() {
        campoObrigatorio = new RequiredFieldValidator();
        campoObrigatorio.setMessage("Obrigatório");
    }
    
    private void definirValidacao() {
        txtCpf.setValidators(campoObrigatorio);
        txtQuarto.setValidators(campoObrigatorio);
                
    }
    
    private boolean validate() {
        txtCpf.validate();
        txtQuarto.validate();
                
        return txtCpf.validate() && txtQuarto.validate();
    }
}
