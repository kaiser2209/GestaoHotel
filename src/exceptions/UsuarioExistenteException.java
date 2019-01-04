/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author guard
 */
public class UsuarioExistenteException extends Exception {
    public UsuarioExistenteException() {
        super("Já existe usuário cadastrado com o CPF informado!");
    }
}
