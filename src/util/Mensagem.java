/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
    
    public static void mensagemDeErroBD() {
        mensagemDeErro("Erro de comunicação com "
                + "o Banco de Dado!\nProcure o administrador "
                + "do sistema");
    }
}
