/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author guard
 */
public class Mensagem {
    public static Optional<ButtonType> mensagemDeConfirmacao(String mensagem, String titulo){
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle(titulo);
        conf.setHeaderText(null);
        conf.setContentText(mensagem);
        return conf.showAndWait();
    }
    
    public static void mensagemDeErro(String mensagem) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERRO");
        a.setHeaderText(null);
        a.setContentText(mensagem);
        a.showAndWait();
    }
    
    public static void mensagem(String mensagem) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Aviso");
        a.setHeaderText(null);
        a.setContentText(mensagem);
        a.showAndWait();
    }
    
    public static void mensagemDeErroBD() {
        mensagemDeErro("Erro de comunicação com "
                + "o Banco de Dado!\nProcure o administrador "
                + "do sistema");
    }
    
    public static void mensagemDeErro(SQLException e) {
        mensagemDeErro("Houve um erro ao tentar executar a ação solicitada!\n"
                + "Entre em contato com o administrador do sistema!\n"
                + "Código do Erro: " + e.getErrorCode());
    }
    
    public static void mensagemCamposInvalidos() {
        mensagemDeErro("Não foi possível executar a ação solicitada.\n"
                + "Verifique os campos digitados e tente novamente.");
    }
}
